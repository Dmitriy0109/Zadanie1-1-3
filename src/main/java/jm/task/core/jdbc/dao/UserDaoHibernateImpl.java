package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS UserTable " +
                "( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(50),lastName VARCHAR(50)," +
                "age TINYINT)";
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery(createTable).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS UserTable";
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery(dropTable).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(new User(name,lastName,age));
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        User user=session.get(User.class,id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session=sessionFactory.openSession();
        transaction=session.beginTransaction();
        List<User>users=session.createQuery("from User").getResultList();
        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session=sessionFactory.openSession();
        transaction=session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE usertable;").executeUpdate();
        transaction.commit();
        session.close();
    }
}
