package org.newsportal.service.mapper;

import org.newsportal.database.repository.entity.Article;

import java.util.List;

public interface ArticleMapper {
    Article mapToDatabase(org.newsportal.service.model.Article source);

    org.newsportal.service.model.Article mapToService(Article source);

    List<Article> mapToDatabase(List<org.newsportal.service.model.Article> source);

    List<org.newsportal.service.model.Article> mapToService(List<Article> source);
}
