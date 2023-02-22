package org.newsportal.database.repository.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.util.HibernateUtil;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {
    private final SessionFactory sessionFactory;

    public ArticleRepositoryImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Article> findAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Article> criteriaQuery = builder.createQuery(Article.class);
            Root<Article> root = criteriaQuery.from(Article.class);
            criteriaQuery.select(root);
            Query query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    @Override
    public Article findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Article.class, id);
        }
    }

    @Override
    public Article findByTitle(String title) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaQuery<Article> articleCriteriaQuery = session.getCriteriaBuilder().createQuery(Article.class);
            articleCriteriaQuery.select(articleCriteriaQuery.from(Article.class));
            articleCriteriaQuery.where(session.getCriteriaBuilder().equal(articleCriteriaQuery.from(Article.class).get("title"), title));
            return session.createQuery(articleCriteriaQuery).getSingleResult();
        }
    }

    @Override
    public Article createArticle(Article article) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(article);
            session.getTransaction().commit();
            return article;
        }
    }

    @Override
    public Article updateArticleById(Long id, Article article) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Article articleToUpdate = session.get(Article.class, id);
            articleToUpdate.setTitle(article.getTitle());
            articleToUpdate.setContent(article.getContent());
            articleToUpdate.setUser(article.getUser());
            session.update(articleToUpdate);
            session.getTransaction().commit();
            return articleToUpdate;
        }
    }

    @Override
    public void deleteArticleById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Article.class, id));
            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
        ArticleRepository articleRepository = new ArticleRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();

//        Article article = new Article();
//        article.setTitle("First Article");
//        article.setContent("Very interesting article");
//        article.setUser(userRepository.findById(1L));
//
//        Article article1 = new Article();
//        article1.setTitle("Second article");
//        article1.setContent("Not so much interesting article");
//        article1.setUser(userRepository.findById(2L));
//        articleRepository.createArticle(article);
//        articleRepository.createArticle(article1);

    }
}
