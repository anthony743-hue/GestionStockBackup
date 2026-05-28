package utilities;

import java.sql.*;

public class UtilDB {
    private String url = "";
    private String user = "";
    private String password = "";
    private String database = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String temp_url = url + database;
        return DriverManager.getConnection(temp_url, user, password);
    }
}
