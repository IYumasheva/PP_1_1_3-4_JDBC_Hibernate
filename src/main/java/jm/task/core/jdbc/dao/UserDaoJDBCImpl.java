package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connect = null;
    private final static String TABLENAME = "table";

    public UserDaoJDBCImpl() {
        connect = Util.getConnection();
    }

    public void createUsersTable() {

        String str = "CREATE TABLE IF NOT EXISTS preproj_m1." + TABLENAME + " ("
                + "id MEDIUMINT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(255) NULL, "
                + "lastname VARCHAR(255) NULL, "
                + "age TINYINT NULL, "
                + "PRIMARY KEY (id) "
                + ")";
        try {
            PreparedStatement ps = connect.prepareStatement(str);
            ps.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS preproj_m1." + TABLENAME;
        try (PreparedStatement ps = connect.prepareStatement(drop)) {
            ps.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserToDB = "INSERT INTO preproj_m1." + TABLENAME + " (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(saveUserToDB)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, (byte) age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String delById = "DELETE FROM preproj_m1." + TABLENAME + " WHERE id = " + id;
        try (PreparedStatement ps = connect.prepareStatement(delById))
        {
            ps.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();
        String query = "SELECT * FROM preproj_m1." + TABLENAME;

        try (Statement statement = connect.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String del = "TRUNCATE TABLE preproj_m1." + TABLENAME;
        try (PreparedStatement ps = connect.prepareStatement(del))
        {
            ps.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
