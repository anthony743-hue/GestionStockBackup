package models;

public class Entity {
    private String tableName;
    private String[] notAllowedFields;
    private String[] allowedFields;

    public String getTableName() {
        return tableName;
    }

    public String[] getNotAllowedFields() {
        return notAllowedFields;
    }

    public void setNotAllowedFields(String[] notAllowedFields) {
        this.notAllowedFields = notAllowedFields;
    }

    public String[] getAllowedFields() {
        return allowedFields;
    }

    public void setAllowedFields(String[] allowedFields) {
        this.allowedFields = allowedFields;
    }

    public void setTableName(String tableName) throws Exception {
        if (tableName == null) {
            throw new Exception("Le nom de la table ne doit pas etre null");
        }
        this.tableName = tableName;
    }
}
