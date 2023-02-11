package org.newsportal.database.repository.mongo;

import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.User;

import java.util.List;

public class UserMongoRepositoryImpl implements UserRepository {
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
