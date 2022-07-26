package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
    private static final String userName = "admin";
    private static final String password = "admin";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public Util() {
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties prop = new Properties();
                prop.put(Environment.URL, url);
                prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                prop.put(Environment.USER, userName);
                prop.put(Environment.PASS, password);
                prop.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                prop.put(Environment.SHOW_SQL, "true");
                prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                prop.put(Environment.HBM2DDL_AUTO, "update");
                sessionFactory = new Configuration()
                        .addProperties(prop)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                System.out.println("SessionFactory creation failed" + e);
            }
        }

        return sessionFactory;
    }


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println("Ошибка подключения к БД");
        }
        return null;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

