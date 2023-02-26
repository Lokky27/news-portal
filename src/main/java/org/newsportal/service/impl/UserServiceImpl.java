package org.newsportal.service.impl;

import org.newsportal.database.repository.UserRepository;
import org.newsportal.service.UserService;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.of(userMapper.mapToService(userRepository.findAll()));
    }

    @Override
    public Optional<User> getById(Long id) {
        User userFromDb = userMapper.mapToService(userRepository.findById(id));
        return Optional.of(userFromDb);
    }

    @Override
    public Optional<User> getByUsername(String userName) {

        return Optional.of(userMapper.mapToService(userRepository.findByUsername(userName)));
    }

    @Override
    public Optional<User> createUser(User user)
    {
        org.newsportal.database.repository.entity.User savedUser = userMapper.mapToDatabase(user);
        return Optional.of(userMapper.mapToService(userRepository.createUser(savedUser)));
    }

    @Override
    public Optional<User> changeUserById(Long id, User user) {
        org.newsportal.database.repository.entity.User userToUpdateInDatabase = userMapper.mapToDatabase(user);
        return Optional.of(userMapper.mapToService(userRepository.updateUserById(id, userToUpdateInDatabase)));
    }

    @Override
    public void removeUserById(Long id) {

        userRepository.deleteUserById(id);
    }
}
