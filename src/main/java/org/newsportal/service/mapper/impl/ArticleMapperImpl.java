package org.newsportal.service.mapper.impl;

import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.service.mapper.ArticleMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleMapperImpl implements ArticleMapper {
    @Override
    public Article mapToDatabase(org.newsportal.service.model.Article source) {
        if (source == null) return null;
        User user = null;
        if (source.getUser() != null) {
            user.setId(source.getUser().getId());
            user.setUsername(source.getUser().getUsername());
            user.setPassword(source.getUser().getPassword());
        }

        return new Article(source.getId(), source.getTitle(), source.getContent(), user);
    }

    @Override
    public org.newsportal.service.model.Article mapToService(Article source) {
        return null;
    }

    @Override
    public List<Article> mapToDatabase(List<org.newsportal.service.model.Article> source) {
        List<Article> entities = new ArrayList<>();
        for (org.newsportal.service.model.Article article : source) {
            entities.add(mapToDatabase(article));
        }
        return entities;
    }

    @Override
    public List<org.newsportal.service.model.Article> mapToService(List<Article> source) {
        return source.stream().map(this::mapToService).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
