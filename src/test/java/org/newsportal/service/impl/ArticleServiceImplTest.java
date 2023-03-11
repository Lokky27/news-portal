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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

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
        articleService.changeArticleById(eq(anyLong()), any(org.newsportal.service.model.Article.class));
        verify(mapper).mapToService((Article) any());
        verify(repository).updateArticleById(anyLong(), any());
    }

    @Test
    void removeUserById() {
        articleService.removeUserById(anyLong());
        verify(mapper, times(1)).mapToService((Article) any());
        verify(repository, times(1)).deleteArticleById(anyLong());
    }
}