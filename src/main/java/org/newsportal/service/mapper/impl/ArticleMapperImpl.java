package org.newsportal.service.mapper.impl;


import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class ArticleMapperImpl implements ArticleMapper {

    private final UserRepository userRepository;

    public ArticleMapperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Article mapToDatabase(org.newsportal.service.model.Article source) {
        if (source == null) return null;
        User user = null;
        if (source.getUsername() != null) {
            user = userRepository.findByUsername(source.getUsername());
        }
        return Article.builder()
                .id(source.getId())
                .title(source.getTitle())
                .content(source.getContent())
                .user(user)
                .build();
    }

    @Override
    public org.newsportal.service.model.Article mapToService(Article source) {
        if (source == null) return null;
        String username = null;
        if (source.getUser() != null) {
            username = source.getUser().getUsername();
        }
        return org.newsportal.service.model.Article.builder()
                .id(source.getId())
                .title(source.getTitle())
                .content(source.getContent())
                .username(username)
                .build();
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