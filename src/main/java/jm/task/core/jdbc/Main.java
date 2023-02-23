package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

        userDaoHibernate.createUsersTable(); // работает
        userDaoHibernate.saveUser("Name", "LastName", (byte) 65); // works
        userDaoHibernate.removeUserById(6); // works

        List<User> users = userDaoHibernate.getAllUsers();
        for (User user: users) {
            System.out.println(user.toString());
        }

        userDaoHibernate.cleanUsersTable(); // Работает
        userDaoHibernate.dropUsersTable(); // Работает
    }
}
