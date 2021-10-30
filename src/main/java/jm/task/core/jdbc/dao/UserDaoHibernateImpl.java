package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = Util.getHibernateConnection();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ("
                + " id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                + " name VARCHAR (128) NOT NULL, "
                + " lastName VARCHAR (128) NOT NULL, "
                + " age INT(64) NOT NULL)")
                .executeUpdate();
        session.getTransaction()
                .commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction()
                .commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction()
                .commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction()
                .commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list;

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        list = session.createQuery("FROM users").getResultList();
        session.getTransaction()
                .commit();
        session.close();

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM users").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
