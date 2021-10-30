package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try ( Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + " id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + " name VARCHAR (128) NOT NULL, "
                    + " lastName VARCHAR (128) NOT NULL, "
                    + " age INT(64) NOT NULL)");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try( Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?,?,?)")) {
            preparedStatement.setString( 1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try ( Statement statement = connection.createStatement();){
            statement.executeUpdate("DELETE FROM users WHERE id = " + id + ";" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM users;");

            while (rs.next()) {
                long id = rs.getInt(1);
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                int age = rs.getInt(4);

                User user = new User(name, lastName, (byte) age);
                user.setId(id);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
       try (Statement statement = connection.createStatement()) {
           statement.executeUpdate("DELETE  FROM users;");
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
}
