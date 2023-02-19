package org.newsportal.service.impl;

import org.newsportal.database.repository.UserRepository;
import org.newsportal.service.UserService;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByUsername(String userName) {
        return Optional.empty();
    }

    @Override
    public Optional<User> createUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> changeUserById(Long id, User user) {
        return Optional.empty();
    }

    @Override
    public void removeUserById(Long id) {

    }
}
