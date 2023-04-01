package org.newsportal.service.mapper.impl;

import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.impl.ArticleRepositoryImpl;
import org.newsportal.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapperImpl implements UserMapper {
    private final ArticleRepository articleRepository;

    public UserMapperImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public User mapToDatabase(org.newsportal.service.model.User source) {
        if(source == null) return null;
        Set<Article> articles;
        if (source.getArticles() != null) {
            articles = source.getArticles().stream()
                    .filter(Objects::nonNull)
                    .map(articleRepository::findByTitle)
                    .collect(Collectors.toSet());
        }
        else articles = new HashSet<>();

        return User.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password(source.getPassword())
                .articleSet(articles)
                .build();
    }

    @Override
    public org.newsportal.service.model.User mapToService(User source) {
        if (source == null) return null;
        Set<String> articles =  source.getArticleSet().stream()
                .map(Article::getTitle)
                .collect(Collectors.toSet());

        return org.newsportal.service.model.User.builder()
                .id(source.getId())
                .username(source.getUsername())
                .password(source.getPassword())
                .articles(articles)
                .build();
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
