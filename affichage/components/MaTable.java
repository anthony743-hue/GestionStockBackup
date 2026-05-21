package affichage.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import utilities.MethodUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MaTable extends JTable {

    private List<?> items; 
    private String[] columnNames;    

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public MaTable(Collection<?> data) throws Exception {
        setData(data);
        init();
    }

    public MaTable() throws Exception{

    }

    private void initColumnName() throws Exception{
        if (!items.isEmpty()) {
            Class<?> clazz = items.get(0).getClass();
            Field[] tousLesChamps = clazz.getDeclaredFields();
            columnNames = new String[tousLesChamps.length];
            for (int i=0; i < tousLesChamps.length; i++) {
                Field f = tousLesChamps[i];
                f.setAccessible(true);
                columnNames[i] = f.getName();
            }
        } else {
            columnNames = new String[0];
        }
    }

    private void init() throws Exception {
        initColumnName();
        Object[][] donnees = new Object[items.size()][columnNames.length];
        Object obj = null, val = null;
        for (int row = 0; row < items.size(); row++) {
            obj = items.get(row);
            for (int col = 0; col < columnNames.length; col++) {
                val = MethodUtils.getVal(columnNames[col], obj);
                donnees[row][col] = (val != null) ? val.toString() : "";
            }
        }
        DefaultTableModel model = new DefaultTableModel(donnees, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setModel(model);
    }

    public Object getValue() {
        int row = getSelectedRow();
        if (row >= 0 && row < items.size()) {
            int modelRow = convertRowIndexToModel(row);
            if (modelRow >= 0 && modelRow < items.size()) {
                return items.get(modelRow);
            }
        }
        return null;
    }

    public void clear(){
        DefaultTableModel tableModel = (DefaultTableModel) getModel();
        if (tableModel != null) {
            tableModel.setRowCount(0);
        }
    }

    public void refresh() throws Exception {
        DefaultTableModel tableModel = (DefaultTableModel) getModel();
        if (tableModel == null || items == null || columnNames == null) {
            return;
        }


        for (int row = 0; row < items.size(); row++) {
            Object obj = items.get(row);
            for (int col = 0; col < columnNames.length; col++) {
                Object val = MethodUtils.getVal(columnNames[col], obj);
                tableModel.setValueAt((val != null) ? val.toString() : "", row, col);
            }
        }
    }

    public void setData(Collection<?> data) throws Exception {
        if (data == null) {
            throw new Exception("le data doit etre  initialisee");
        }
        this.items = new ArrayList<>(data);
    }

    public List<?> getItems() {
        return items;
    }
}