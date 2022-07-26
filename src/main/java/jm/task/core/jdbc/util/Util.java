package jm.task.core.jdbc.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
    private static final String userName = "admin";
    private static final String password = "admin";
    private static Connection connection;

    public Util() {}

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection= DriverManager.getConnection(url, userName, password);
        } catch (SQLException  | ClassNotFoundException exception) {
            System.out.println("Ошибка подключения к БД");
        }
        return null;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

