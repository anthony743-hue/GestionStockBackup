package service;

import dao.GenericDAO;
import utilities.UtilDB;
import java.util.ArrayList;
import java.util.Map;
import utilities.Mapping;

public class GenericService {
    private GenericDAO dao;

    public GenericService() throws Exception {
        UtilDB db = new UtilDB();
        String url = "jdbc:postgresql://localhost:5432/gestionstock";
        String user = "postgres";
        String password = "postgres";
        db.setPassword(password);
        db.setUrl(url);
        db.setUser(user);
        dao = new GenericDAO(db);
    }

    public GenericService(Map<String, Mapping> map) throws Exception {
        this();
        GenericDAO.setMap(map);
    }

    public void setMapping(Map<String, Mapping> map) {
        GenericDAO.setMap(map);
    }

    public void save(Object o) throws Exception {
        dao.save(o);
    }

    public void update(Object o) throws Exception {
        dao.update(o);
    }

    public void getById(Object o) throws Exception {
        dao.getById(o);
    }

    public ArrayList<Object> findAll(Object obj) throws Exception {
        return dao.findAll(obj);
    }
}