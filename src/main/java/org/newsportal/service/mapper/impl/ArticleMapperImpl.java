package org.newsportal.service.mapper.impl;

import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class ArticleMapperImpl implements ArticleMapper {

//    private final UserMapper userMapper;
//
//    public ArticleMapperImpl(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }

    @Override
    public Article mapToDatabase(org.newsportal.service.model.Article source) {
        if (source == null) return null;
        User user = null;
        if (source.getUser() != null) {
//            user = userMapper.mapToDatabase(source.getUser());
            user = new User();
            user.setId(source.getUser().getId());
            user.setUsername(source.getUser().getUsername());
            user.setPassword(source.getUser().getPassword());
            source.getUser().getArticleSet().stream()
                    .map(this::mapToDatabase)
                    .collect(Collectors.toSet());

        }
        return new Article(source.getId(),
                source.getTitle(),
                source.getContent(),
                user);
    }

    @Override
    public org.newsportal.service.model.Article mapToService(Article source) {
        if (source == null) return null;
        org.newsportal.service.model.User user = null;
        if (source.getUser() != null) {
            user = new org.newsportal.service.model.User();
            user.setId(source.getUser().getId());
            user.setPassword(source.getUser().getPassword());
            user.setUsername(source.getUser().getUsername());
            source.getUser().getArticleSet().stream()
                    .map(this::mapToService)
                    .collect(Collectors.toSet());
        }
        return new org.newsportal.service.model.Article(source.getId(),
                source.getTitle(),
                source.getContent(),
                user);
    }

    @Override
    public List<Article> mapToDatabase(List<org.newsportal.service.model.Article> source) {

        return source.stream()
                .map(this::mapToDatabase)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<org.newsportal.service.model.Article> mapToService(List<Article> source) {
        return source.stream()
                .map(this::mapToService)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}