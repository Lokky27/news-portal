package org.newsportal.service.mapper.impl;

import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapperImpl implements UserMapper {
    private ArticleMapper articleMapper;

    public UserMapperImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }
    @Override
    public User mapToDatabase(org.newsportal.service.model.User source) {
        if(source == null) return null;
        Set<Article> articleSet = source.getArticleSet()
                .stream()
                .map(articleMapper::mapToDatabase)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return new User(source.getId(), source.getUsername(), source.getPassword(), articleSet);
    }

    @Override
    public org.newsportal.service.model.User mapToService(User source) {
        if (source == null) return null;
        Set<org.newsportal.service.model.Article> articleSet = source.getArticleSet()
                .stream()
                .map(articleMapper::mapToService)
                .collect(Collectors.toSet());

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
}
