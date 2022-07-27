package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS UserTable " +
                "( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(50),lastName VARCHAR(50)," +
                "age TINYINT)";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(createTable).executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS UserTable";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(dropTable).executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User>users = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
             users = session.createQuery("from User").getResultList();
            transaction.commit();
            return users;
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()){
        transaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE usertable;").executeUpdate();
        transaction.commit();
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
