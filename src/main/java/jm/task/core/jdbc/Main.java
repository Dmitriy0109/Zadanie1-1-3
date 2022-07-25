package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl user=new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Damir","Ivanov", (byte) 26);
        user.saveUser("Sergei","Novikov", (byte) 72);
        user.saveUser("Ivan","Sidorov", (byte) 32);
        user.saveUser("Dima","Ponamarev", (byte) 31);
        System.out.println(user.getAllUsers().toString());
        user.cleanUsersTable();
        user.dropUsersTable();
    }

}
