package utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MethodUtils {
    public static Object getVal(String name, Object o) throws Exception {
        Class<?> clazz = o.getClass();
        Method m = clazz.getMethod("get" + capitalized(name));
        return m.invoke(o);
    }

    public static String capitalized(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String[] getAttributs(Class<?> c) throws Exception {
        ArrayList<String> ls = new ArrayList<>();
        Field[] champs = c.getDeclaredFields();
        for (Field f : champs) {
            f.setAccessible(true);
            ls.add(f.getName());
        }
        return ls.toArray(new String[0]);
    }

    public static void setAttribut(Object temp, ResultSet rs, Mapping m) throws Exception {
        String[] champs = m.getAttributs();
        Class<?>[] types = m.getTypes();
        Class<?> clazz = temp.getClass();
        Object valeur = null;
        for (int i = 0; i < champs.length; i++) {
            String name = champs[i];
            // System.out.println("Nom de champ : " + name);
            Class<?> type = types[i];

            Method method = clazz.getDeclaredMethod("set" + capitalized(name), type);
            
            if (type.equals(int.class) || type.equals(Integer.class)) {
                valeur = rs.getInt(name);
            } else if (type.equals(long.class) || type.equals(Long.class)) {
                valeur = rs.getLong(name);
            } else if (type.equals(double.class) || type.equals(Double.class)) {
                valeur = rs.getDouble(name);
            } else if (type.equals(java.time.LocalDate.class)) {
                java.sql.Date date = rs.getDate(name);
                valeur = (date != null) ? date.toLocalDate() : null;
            } else {
                valeur = rs.getString(name);
            } 
            if (rs.wasNull() && type.isPrimitive()) {
                valeur = primitiveDefault(type);
            }

            method.invoke(temp, valeur);
        }
    }

    private static Object primitiveDefault(Class<?> type) {
        if (type == int.class || type == long.class)
            return 0;
        if (type == double.class)
            return 0.0;
        if (type == boolean.class)
            return false;
        return null;
    }

    public static boolean isNumber(Class<?> type) {
        return type.equals(Integer.class) || type.equals(Float.class)
                || type.equals(Long.class) || type.equals(int.class) || type.equals(float.class);
    }

    // public static void copyProperties(Object source, Object destination) {
    //     if (source == null || destination == null)
    //         return;

    //     try {
    //         // Étape 1 : construire le mapping depuis les champs de la source
    //         Mapping mapping = new Mapping();
    //         mapping.setAttributs(source.getClass().getDeclaredFields());

    //         String[] champs = mapping.getAttributs();
    //         Class<?>[] types = mapping.getTypes();
    //         Class<?> sourceClass = source.getClass();
    //         Class<?> destClass = destination.getClass();

    //         for (int i = 0; i < champs.length; i++) {
    //             String name = champs[i];
    //             Class<?> type = types[i];

    //             // Getter (ex: getNom()) – suppose des getters standards
    //             Method getter = sourceClass.getDeclaredMethod("get" + MethodUtils.capitalized(name));
    //             Object value = getter.invoke(source);

    //             // On ne copie que si la valeur n’est pas nulle (optionnel)
    //             if (value != null) {
    //                 // Setter dans la destination (ex: setNom(...))
    //                 Method setter = destClass.getDeclaredMethod("set" + MethodUtils.capitalized(name), type);
    //                 setter.invoke(destination, value);
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
