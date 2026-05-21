package dao;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import utilities.MethodUtils;
import utilities.Param;
import utilities.Mapping;
import utilities.UtilDB;

public class GenericDAO {
    private UtilDB connectionFactory;
    private static Map<String, Mapping> map;

    public static void setMap(Map<String, Mapping> m) {
        if (m == null) {
            GenericDAO.map = new HashMap<>();
            return;
        }
        GenericDAO.map = m;
    }

    public GenericDAO() throws Exception {

    }

    public GenericDAO(UtilDB connectionFactory) throws Exception {
        setConnectionFactory(connectionFactory);
        setMap(null);
    }

    public GenericDAO(UtilDB conn, Map<String, Mapping> map) throws Exception {
        this(conn);
        setMap(map);
    }

    private void addTable(Object o) throws Exception {
        Mapping m = new Mapping();
        Class<?> c = o.getClass();
        m.setAttributs(c.getDeclaredFields());
        m.setTable(c.getSimpleName());
        map.put(c.getName(), m);
    }

    public void setConnectionFactory(UtilDB connectionFactory) throws Exception {
        if (connectionFactory == null) {
            throw new Exception("Le connection factory doit etre initialise");
        }
        this.connectionFactory = connectionFactory;
    }

    public void save(Object o) throws Exception {
        Connection conn = null;
        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);
            save(o, conn);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
                throw e;
            }
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void save(Object o, Connection conn) throws Exception {
        if (!map.containsKey(o.getClass().getName())) {
            addTable(o);
        }
        Mapping m1 = map.get(o.getClass().getName());
        String[] attributs = m1.getAttributs();
        StringBuffer sb = new StringBuffer("INSERT INTO " + m1.getTable() + "(");
        sb.append(String.join(",", attributs)).append(") VALUES (");

        for (int i = 0; i < attributs.length; i++) {
            sb.append("?");
            if (i < attributs.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");

        try (PreparedStatement pstmt = conn.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < attributs.length; i++) {
                pstmt.setObject(i + 1, MethodUtils.getVal(attributs[i], o));
            }
            int result = pstmt.executeUpdate();
            if (result < 0) {
                throw new Exception("insertion echouee");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Method m = o.getClass().getDeclaredMethod("setId", int.class);
                    m.invoke(o, rs.getInt(1));
                } else {
                    throw new Exception("insertion echouee");
                }
            }
        }
    }

    public ArrayList<Object> findAll(Object o) throws Exception {
        if (!map.containsKey(o.getClass().getName())) {
            addTable(o);
        }
        Mapping m1 = map.get(o.getClass().getName());
        String[] attributs = m1.getAttributs();
        Class<?>[] types = m1.getTypes();

        StringBuffer sb = new StringBuffer("SELECT ");
        sb.append(String.join(",", attributs)).append(" FROM " + m1.getTable()).append(" WHERE 1 = 1");

        ArrayList<Param> params = new ArrayList<>();
        Class<?> type = null;
        String name = null;
        Object val = null;
        Number n = null;
        for (int i = 0; i < attributs.length; i++) {
            type = types[i];
            name = attributs[i];
            val = MethodUtils.getVal(name, o);

            if (val == null || (MethodUtils.isNumber(type) && (n = (Number) val).intValue() == 0)) {
                continue;
            }
            params.add(new Param(name, val));
        }

        for (int i = 0; i < params.size(); i++) {
            name = params.get(i).getName();
            sb.append(" AND " + name + " = ?");
        }

        ArrayList<Object> ls = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sb.toString())) {

            for (int i = 0; i < params.size(); i++) {
                val = params.get(i).getVal();
                stmt.setObject(i + 1, val);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                Object temp = null;

                Constructor construct = o.getClass().getConstructor();

                while (rs.next()) {
                    temp = construct.newInstance();
                    MethodUtils.setAttribut(temp, rs, m1);
                    ls.add(temp);
                }
                return ls;
            }
        }
    }

    public void getById(Object o) throws Exception {
        if (!map.containsKey(o.getClass().getName())) {
            addTable(o);
        }
        Mapping m1 = map.get(o.getClass().getName());
        String[] attributs = m1.getAttributs();

        StringBuffer sb = new StringBuffer("SELECT ");
        sb.append(String.join(",", attributs)).append(" FROM " + m1.getTable() + " WHERE id = ? ");

        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {

            Method m = o.getClass().getDeclaredMethod("getId");
            int index = (int) m.invoke(o);
            pstmt.setInt(1, index);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MethodUtils.setAttribut(o, rs, m1);
                }
            }

        }
    }

    public void insertBatch(Collection<?> data) throws Exception {
        Connection conn = null;
        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);
            insertBatch(data, conn);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
                throw e;
            }
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void insertBatch(Collection<?> data, Connection conn) throws Exception {
        ArrayList<?> ls = new ArrayList<>(data);
        Object o = ls.get(0);
        if (!map.containsKey(o.getClass().getName())) {
            addTable(o);
        }

        Mapping m1 = map.get(o.getClass().getName());
        String[] attributs = m1.getAttributs();
        StringBuffer sb = new StringBuffer(String.format("INSERT INTO %s VALUES ", m1.getTable()));
        sb.append("(");

        for (int i = 0; i < attributs.length; i++) {
            if (attributs[i].equalsIgnoreCase("id")) {
                continue;
            }
            sb.append("?");
            if (i < attributs.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");

        String sql = sb.toString();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int paramIndex = 0;
            for (Object obj : ls) {
                paramIndex = 1;
                for (int i = 0; i < attributs.length; i++) {
                    if (attributs[i].equalsIgnoreCase("id")) {
                        continue;
                    }
                    pstmt.setObject(paramIndex++, MethodUtils.getVal(attributs[i], obj));
                }
                pstmt.addBatch();
            }
            int[] results = pstmt.executeBatch();
            for (int res : results) {
                if (res < 0) {
                    throw new Exception("");
                }
            }
        }
    }

    public void update(Object o) throws Exception {
        Connection conn = null;
        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);
            update(o, conn);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
                throw e;
            }
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void update(Object o, Connection conn) throws Exception {
        if (!map.containsKey(o.getClass().getName())) {
            addTable(o);
        }
        Mapping m1 = map.get(o.getClass().getName());

        StringBuffer sb = new StringBuffer("UPDATE " + m1.getTable() + " SET ");
        String[] attributs = m1.getAttributs();
        Class<?>[] types = m1.getTypes();

        ArrayList<Param> params = new ArrayList<>();
        int n = attributs.length;
        String name = null;
        Object val = null;
        for (int i = 0; i < n; i++) {
            name = attributs[i];
            val = MethodUtils.getVal(name, o);

            if (name.equalsIgnoreCase("id") || val == null
                    || types[i].equals(Integer.class))
                continue;

            params.add(new Param(name, val));
        }

        for (int i = 0; i < params.size(); i++) {
            name = params.get(i).getName();
            sb.append(name + " = ?");
            if (i < params.size() - 1) {
                sb.append(", ");
            }
        }

        Method m = o.getClass().getDeclaredMethod("getId");
        int index = (int) m.invoke(o);
        sb.append(" WHERE id = " + index);

        try (PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i).getVal());
            }
            int result = pstmt.executeUpdate();
            if (result < 0) {
                throw new Exception("update echouee");
            }
        }
    }
}