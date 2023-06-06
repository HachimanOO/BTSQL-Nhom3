package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/sms";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    private Connection connection;
    public void init() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);

        } catch (ClassNotFoundException e) {
            throw  new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Database(){
        init();
    }
    public Connection getConnection() {
        // abc
        // abc
        // abc
        // abc
        // abc
        // abc
        // abc
        return connection;
    }
}
