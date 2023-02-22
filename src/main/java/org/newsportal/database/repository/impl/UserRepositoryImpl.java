package org.newsportal.database.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.util.HibernateUtil;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<User> findAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);

            Query query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> findUserByIdCriteriaQuery = builder.createQuery(User.class);
            Root<User> root = findUserByIdCriteriaQuery.from(User.class);
            findUserByIdCriteriaQuery.where(builder.equal(root.get("id"), id));
            findUserByIdCriteriaQuery.select(root);
            Query query = session.createQuery(findUserByIdCriteriaQuery);

            return null;
        }
    }

    @Override
    public User findByUsername(String userName) {
        return null;
    }

    @Override
    public User createUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> saveUserCriteriaQuery = builder.createCriteriaUpdate(User.class);
            Root<User> root = saveUserCriteriaQuery.from(User.class);


        }
        return null;
    }

    @Override
    public User updateUserById(Long id, User user) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> updateUserCriteriaQuery = builder.createCriteriaUpdate(User.class);
            Root<User> root = updateUserCriteriaQuery.from(User.class);
            updateUserCriteriaQuery.set("username", user.getUsername());
            updateUserCriteriaQuery.set("password", user.getPassword());
            updateUserCriteriaQuery.where(builder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(updateUserCriteriaQuery).executeUpdate();
            transaction.commit();

        }
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.where(builder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaQuery).executeUpdate();
            transaction.commit();
        }

    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        System.out.println(userRepository.);
    }
}
