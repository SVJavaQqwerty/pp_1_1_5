package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;


public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/pp_java";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1111";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    private static final Configuration configuration = new Configuration()
            .setProperty("hibernate.connection.driver_class", DRIVER)
            .setProperty("hibernate.connection.url", HOST)
            .setProperty("hibernate.connection.username", LOGIN)
            .setProperty("hibernate.connection.password", PASSWORD)
            .setProperty("hibernate.dialect", DIALECT)
            .addAnnotatedClass(User.class);

    // Требуемый метод создания сессий
    public  static SessionFactory buildSessionFactory() {
        if(sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, DRIVER);
                properties.put(Environment.URL, HOST);
                properties.put(Environment.USER, LOGIN);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, DIALECT);
                sessionFactory = new Configuration()
                        .setProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    private Util() {
    }
    // оставил для себя как пример
    public static SessionFactory getConnection(){
        if(serviceRegistry == null) {
            try {
                serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if(sessionFactory == null) {
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        }
        return sessionFactory;
    }
}