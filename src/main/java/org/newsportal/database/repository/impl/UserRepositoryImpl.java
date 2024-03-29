package org.newsportal.database.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.util.HibernateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
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
            return session.get(User.class, id);
        }
    }

    @Override
    public User findByUsername(String userName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.select(criteriaQuery.from(User.class));
            criteriaQuery.where(session.getCriteriaBuilder().equal(criteriaQuery.from(User.class).get("username"), userName));

            return session.createQuery(criteriaQuery).setMaxResults(1).uniqueResult();
        }
    }

    @Override
    public User createUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        }
    }

    @Override
    @Transactional
    public User updateUserById(Long id, User user) {
        try(Session session = sessionFactory.openSession()) {
            User userToUpdate = session.get(User.class, id);
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            session.beginTransaction();
            session.update(userToUpdate);
            session.getTransaction().commit();
            return userToUpdate;
        }
    }

    @Override
    @Transactional
    public Boolean deleteUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            return null == session.get(User.class, id);
        }
    }
}
