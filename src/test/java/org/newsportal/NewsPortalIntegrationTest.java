package org.newsportal;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
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

import java.util.HashSet;
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
    private static User userEntity;
    private static Article articleEntity;

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

        userRepository = new UserRepositoryImpl(sessionFactory);
        articleRepository = new ArticleRepositoryImpl(sessionFactory);
        articleMapper = new ArticleMapperImpl(userRepository);
        articleService = new ArticleServiceImpl(articleRepository, articleMapper);

        userMapper = new UserMapperImpl(articleRepository);
        userService = new UserServiceImpl(userRepository, articleRepository, userMapper, articleMapper);

        modelUser = org.newsportal.service.model.User.builder()
                .username("Updated first user")
                .password("qwerty12345")
                .build();

        modelArticle = org.newsportal.service.model.Article.builder()
                .title("Test article")
                .content("Content of the test article")
                .username(modelUser.getUsername())
                .build();

    }

    @Test
    public void newsPortalIntegrationTest() {
        org.newsportal.service.model.User expectedUser = org.newsportal.service.model.User.builder()
                .id(1L)
                .username(modelUser.getUsername())
                .password(modelUser.getPassword())
                .build();
        org.newsportal.service.model.User createdUser = userService.createUser(modelUser).get();
        Assertions.assertThat(createdUser.getUsername()).isEqualTo(expectedUser.getUsername());

        org.newsportal.service.model.Article expectedArticle = org.newsportal.service.model.Article.builder()
                .id(1L)
                .title(modelArticle.getTitle())
                .content(modelArticle.getContent())
                .username(modelUser.getUsername())
                .build();

        org.newsportal.service.model.Article createdArticle = articleService.createArticle(modelArticle).get();
        Assertions.assertThat(createdArticle.getTitle()).isEqualTo(expectedArticle.getTitle());

        org.newsportal.service.model.Article updatedExpectedArticle = org.newsportal.service.model.Article.builder()
                .title("Article was updated")
                .content("Test update Article content")
                .username(modelUser.getUsername())
                .build();
        org.newsportal.service.model.Article updatedArticle = articleService.changeArticleById(1L, updatedExpectedArticle).get();
        Assertions.assertThat(updatedArticle.getTitle()).isEqualTo("Article was updated");
        Assertions.assertThat(updatedArticle.getContent()).isEqualTo("Test update Article content");
        Assertions.assertThat(updatedArticle.getId()).isEqualTo(1L);
    }
    @AfterAll
    public static void shutDown() {
        POSTGRE_SQL_CONTAINER.stop();
    }
}
