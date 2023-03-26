package org.newsportal.service.mapper.impl;

import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.impl.UserRepositoryImpl;
import org.newsportal.database.repository.util.HibernateUtil;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User mapToDatabase(org.newsportal.service.model.User source) {
        if(source == null) return null;
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        return user;
    }

    @Override
    public org.newsportal.service.model.User mapToService(User source) {
        if (source == null) return null;
        Set<org.newsportal.service.model.Article> articleSet;
        if (!source.getArticleSet().isEmpty()) {
            articleSet = source.getArticleSet().stream().map(article -> {
                return org.newsportal.service.model.Article.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .user(org.newsportal.service.model.User.builder()
                                .id(source.getId())
                                .username(source.getUsername())
                                .password(source.getPassword())
                                .build())
                        .build();
            }).collect(Collectors.toSet());
        }
        else {
            articleSet = new HashSet<>();
        }
        return new org.newsportal.service.model.User(source.getId(),
                source.getUsername(),
                source.getPassword(),
                articleSet);
    }

    @Override
    public List<User> mapToDatabase(List<org.newsportal.service.model.User> source) {
        return source.stream()
                .map(this::mapToDatabase)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<org.newsportal.service.model.User> mapToService(List<User> source) {
        return source.stream()
                .map(this::mapToService)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        UserRepository repository = new UserRepositoryImpl(HibernateUtil.getSessionFactory());
        User userFromDb = repository.findById(3L);
        UserMapper mapper = new UserMapperImpl();

        org.newsportal.service.model.User userModel = mapper.mapToService(userFromDb);

        System.out.println(userModel.toString());
    }
}
