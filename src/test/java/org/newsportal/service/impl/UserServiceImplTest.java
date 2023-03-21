package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.service.mapper.UserMapper;

import java.util.HashSet;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository repository;
    private static User user;
    private static Article article;

    @BeforeAll
    public static void init() {
        user = new User(1L, "Test user", "qwerty12345", new HashSet<>());
        article = new Article(1L, "Test article", "Content of test article", user);
    }
    @Test
    void getAll() {
        userService.getAll();
        verify(userMapper).mapToService(anyList());
        verify(repository).findAll();
    }

    @Test
    void getById() {
        userService.getById(anyLong());
        verify(userMapper).mapToService((User) any());
        verify(repository).findById(anyLong());
    }

    @Test
    void getByUsername() {
        userService.getByUsername(anyString());
        verify(userMapper).mapToService((User) any());
        verify(repository).findByUsername(anyString());
    }

    @Test
    void createUser() {
        userService.createUser((org.newsportal.service.model.User) any());
        verify(userMapper).mapToService((User) any());
        verify(repository).createUser((User) any());
    }

    @Test
    void changeUserById() {
    }

    @Test
    void removeUserById() {
        userService.removeUserById(anyLong());
        verify(repository).deleteUserById(anyLong());
    }
}