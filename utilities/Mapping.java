package utilities;

import java.lang.reflect.Field;

public class Mapping {
    private String Table;
    private String[] attributs;
    private Class<?>[] types;
    public Class<?>[] getTypes() {
        return types;
    }
    public String getTable() {
        return Table;
    }
    public void setTable(String table) {
        Table = table;
    }
    public String[] getAttributs() {
        return attributs;
    }
    public void setAttributs(Field[] attributs) {
        String[] arr = new String[attributs.length];
        Class<?>[] tClasses = new Class<?>[attributs.length];
        for(int i=0; i < attributs.length; i++){
            attributs[i].setAccessible(true);
            arr[i] = attributs[i].getName();
            // System.out.println(String.format("Nom du champ %d : %s", i, arr[i]));
            tClasses[i] = attributs[i].getType();
        }
        this.attributs = arr;
        this.types = tClasses;
    }    
}
