package org.newsportal.database.repository;

import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;

import java.util.List;
import java.util.Set;

public interface UserRepository {
    List<User> findAll();

    User findById(Long id);

    User findByUsername(String userName);

    Set<Article> findArticlesOfUser(User user);

    User createUser(User user);

    User updateUserById(Long id, User user);

    void deleteUserById(Long id);
}
