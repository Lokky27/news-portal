package org.newsportal.database.dao;

import org.newsportal.database.dao.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Long id);

    User findByUsername(String userName);

    User createUser(User user);

    User updateUserById(Long id, User user);

    void deleteUserById(Long id);
}
