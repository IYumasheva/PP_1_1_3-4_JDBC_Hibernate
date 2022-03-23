package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS preproj_m1.users ("
                + "id MEDIUMINT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(255) NULL, "
                + "lastname VARCHAR(255) NULL, "
                + "age TINYINT NULL, "
                + "PRIMARY KEY (id) "
                + ")";

        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS preproj_m1.users";

        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(new User(name, lastName, age));

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "DELETE User WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "FROM User";
        Query query = session.createQuery(hql);
        List<User> users = query.list();

        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sql = "TRUNCATE TABLE preproj_m1.users";

        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
