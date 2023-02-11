package org.newsportal.database.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.util.HibernateUtil;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        return null;
    }

    @Override
    public User findByUsername(String userName) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUserById(Long id, User user) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        System.out.println(userRepository.findAll());
    }
}
