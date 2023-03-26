package org.newsportal;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.impl.ArticleRepositoryImpl;
import org.newsportal.database.repository.impl.UserRepositoryImpl;
import org.newsportal.service.ArticleService;
import org.newsportal.service.UserService;
import org.newsportal.service.impl.ArticleServiceImpl;
import org.newsportal.service.impl.UserServiceImpl;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.mapper.impl.ArticleMapperImpl;
import org.newsportal.service.mapper.impl.UserMapperImpl;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

@Testcontainers
public class NewsPortalIntegrationTest {
    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer();

    private static UserRepository userRepository;
    private static UserMapper userMapper;
    private static UserService userService;
    private static ArticleMapper articleMapper;
    private static ArticleRepository articleRepository;
    private static ArticleService articleService;
    private static SessionFactory sessionFactory;
    private static Properties properties;

    private static org.newsportal.service.model.User modelUser;
    private static org.newsportal.service.model.Article modelArticle;

    @BeforeAll
    public static void setUp() {
        POSTGRE_SQL_CONTAINER.start();
        properties = new Properties();
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.url", POSTGRE_SQL_CONTAINER.getJdbcUrl());
        properties.put("hibernate.connection.username", POSTGRE_SQL_CONTAINER.getUsername());
        properties.put("hibernate.connection.password", POSTGRE_SQL_CONTAINER.getPassword());
        properties.put("show_sql", true);
        properties.put("spring.jpa.properties.hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        sessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Article.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        articleRepository = new ArticleRepositoryImpl(sessionFactory);
        articleMapper = new ArticleMapperImpl();
        articleService = new ArticleServiceImpl(articleRepository, articleMapper);

        userRepository = new UserRepositoryImpl(sessionFactory);
        userMapper = new UserMapperImpl();
        userService = new UserServiceImpl(userRepository, userMapper);

        modelUser = org.newsportal.service.model.User.builder()
                .username("Updated first user")
                .password("qwerty12345")
                .build();

    }

    @Test
    public void newsPortalIntegrationTest() {
        //userService.createUser(modelUser);
        org.newsportal.service.model.User newUser = userService.getByUsername(modelUser.getUsername())
                .orElse(null);
        Assertions.assertEquals(modelUser, newUser);
        modelArticle = org.newsportal.service.model.Article.builder()
                .title("test title")
                .content("test content")
                .user(modelUser)
                .build();
        articleService.createArticle(modelArticle);
        modelUser.getArticleSet().add(modelArticle);
        List<org.newsportal.service.model.Article> articles = articleService.getAll().get();
        Assertions.assertEquals(articles, Collections.singletonList(modelArticle));


    }
    @AfterAll
    public static void shutDown() {
        POSTGRE_SQL_CONTAINER.stop();
    }
}
