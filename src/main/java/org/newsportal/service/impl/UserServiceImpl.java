package org.newsportal.service.impl;

import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.impl.ArticleRepositoryImpl;
import org.newsportal.database.repository.impl.UserRepositoryImpl;
import org.newsportal.database.repository.util.HibernateUtil;
import org.newsportal.service.UserService;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.mapper.impl.ArticleMapperImpl;
import org.newsportal.service.mapper.impl.UserMapperImpl;
import org.newsportal.service.model.Article;
import org.newsportal.service.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;

    public UserServiceImpl(UserRepository userRepository,
                           ArticleRepository articleRepository,
                           UserMapper userMapper,
                           ArticleMapper articleMapper) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.ofNullable(userMapper.mapToService(userRepository.findAll()));
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userMapper.mapToService(userRepository.findById(id)));
    }

    @Override
    public Optional<User> getByUsername(String userName) {

        return Optional.ofNullable(userMapper.mapToService(userRepository.findByUsername(userName)));
    }

    @Override
    public Optional<User> createUser(User user)
    {
        return Optional.ofNullable(userMapper.mapToService(
                userRepository.createUser(userMapper.mapToDatabase(user)))
        );
    }

    @Override
    public Optional<User> addArticleToUser(Long id, Article article) {
        org.newsportal.database.repository.entity.User user = userRepository.findById(id);
        user.getArticleSet().add(articleMapper.mapToDatabase(article));
        articleRepository.createArticle(articleMapper.mapToDatabase(article));
        return Optional.ofNullable(userMapper.mapToService(user));
    }

    @Override
    public Optional<User> changeUserById(Long id, User user) {
        org.newsportal.database.repository.entity.User userToUpdateInDatabase = userMapper.mapToDatabase(user);
        return Optional.ofNullable(userMapper.mapToService(userRepository.updateUserById(id, userToUpdateInDatabase)));
    }

    @Override
    public Optional<Boolean> removeUserById(Long id)
    {
        return Optional.ofNullable(userRepository.deleteUserById(id));
    }

}
