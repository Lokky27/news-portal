package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;

import static org.mockito.ArgumentMatchers.*;

import java.util.HashSet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @InjectMocks
    private ArticleServiceImpl articleService;
    @Mock
    private ArticleMapper mapper;
    @Mock
    private ArticleRepository repository;

    private static User user;
    private static Article article;


    @BeforeAll
    public static void init() {
        user = new User(1l, "first user", "123456", new HashSet<>());
        article = new Article(1l, "test article", "test content", user);
    }
    @Test
    void getAll() {
        articleService.getAll();
        verify(mapper).mapToService(anyList());
        verify(repository).findAll();
    }

    @Test
    void getById() {
        articleService.getById(anyLong());
        verify(mapper).mapToService((Article) any());
        verify(repository).findById(anyLong());
    }

    @Test
    void getByTitle() {
        articleService.getByTitle(anyString());
        verify(mapper).mapToService((Article) any());
        verify(repository).findByTitle(anyString());
    }

    @Test
    void createArticle() {
        articleService.createArticle(any());
        verify(mapper).mapToService((Article) any());
        verify(repository).createArticle(any());

    }

    @Test
    void changeArticleById() {
        org.newsportal.service.model.Article newArticle = new org.newsportal.service.model.Article();
        newArticle.setTitle("A new Article");
        newArticle.setContent("A content of a new article");
        articleService.changeArticleById(anyLong(), newArticle);
        verify(mapper).mapToService((Article) any());
        verify(repository).updateArticleById(anyLong(), any());
    }

    @Test
    void removeUserById() {
        articleService.removeUserById(anyLong());
        verify(repository).deleteArticleById(anyLong());
    }
}