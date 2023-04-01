package org.newsportal.service;

import org.newsportal.service.model.Article;
import org.newsportal.service.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<User>> getAll();

    Optional<User> getById(Long id);

    Optional<User> getByUsername(String userName);

    Optional<User> createUser(User user);

    Optional<User> addArticleToUser(Long id, Article article);

    Optional<User> changeUserById(Long id, User user);

    Optional<Boolean> removeUserById(Long id);
}
