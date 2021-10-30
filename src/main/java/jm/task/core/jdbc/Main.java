package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Vladimir", "Matveyev", (byte) 46);
        userService.saveUser("Alexandr", "Bulgakov", (byte) 31);
        userService.saveUser("Ali", "Veliev", (byte) 28);
        userService.saveUser("Semyon", "Fomichyov", (byte) 30);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
