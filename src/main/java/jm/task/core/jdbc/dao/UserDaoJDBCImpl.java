package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection= Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS UserTable " +
                "( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(15),last_name VARCHAR(15)," +
                "age TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
        } catch (SQLException e) {
            System.out.println("Таблица не создана !!!");
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS UserTable";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTable);
        } catch (SQLException e) {
            System.out.println("Таблица не удалена!");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO UserTable (name,last_Name,age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Пользователь не добавлен!");
        }
    }

    public void removeUserById(long id) {
        String removeUser = "DELETE UserTable WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Пользователь не удален!");
        }
    }

    public List<User> getAllUsers() {
        String getUsers = "SELECT * FROM UserTable ";
        ArrayList listUser = new ArrayList();
        try (PreparedStatement prepareStatement = connection.prepareStatement(getUsers)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            User user = null;

            while (resultSet.next()) {
                user = new User(

                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                );
                listUser.add(user);
            }
            return listUser;

        } catch (SQLException e) {
            System.out.println("Ошибка вывода всей информации из таблицы");
            return listUser;
        }
    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE UserTable";
        try (Statement statement = connection.createStatement()) {
            statement.execute(cleanTable);

        } catch (SQLException exception) {
            System.out.println("Cтроки НЕ удалены");
        }
        System.out.println("Строки удалены");
    }

}
