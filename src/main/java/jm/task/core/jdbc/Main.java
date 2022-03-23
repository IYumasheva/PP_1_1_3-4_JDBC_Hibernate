package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Kate", "Lebovsky", (byte) 34);
        userService.saveUser("John", "Smith", (byte) 20);
        userService.saveUser("Jane", "Dou", (byte) 55);
        userService.saveUser("Steve", "Rott", (byte) 99);

        List<User> users = userService.getAllUsers();
        users.forEach(user -> System.out.println(user));

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
