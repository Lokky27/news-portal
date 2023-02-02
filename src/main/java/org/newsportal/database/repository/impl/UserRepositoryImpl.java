package org.newsportal.database.repository.impl;

import org.newsportal.database.entity.User;
import org.newsportal.database.repository.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> findAll() {
        return null;
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
}
