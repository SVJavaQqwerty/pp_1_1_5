package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(30)," +
                    "lastname VARCHAR(30)," +
                    "age TINYINT," +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица users создана!");
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("Drop table if exists users").executeUpdate();
            transaction.commit();
            System.out.println("Таблица users удалена!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("insert into users set name='"+ name +
                            "', lastName='"+ lastName+ "', age=" + age)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Пользователь: " + name + " " + lastName + " добавлен в таблицу");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь: " + id + " удален");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "select ID, NAME, LASTNAME, AGE from USERS";
        try (Session session = sessionFactory.openSession()) {
            list = session.createSQLQuery(sql).addEntity(User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> users = getAllUsers();
            for (User user : users) session.delete(user);
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
