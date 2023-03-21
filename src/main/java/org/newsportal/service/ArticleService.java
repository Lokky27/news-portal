package org.newsportal.service;

import org.newsportal.service.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<List<Article>> getAll();

    Optional<Article> getById(Long id);

    Optional<Article> getByTitle(String title);

    Optional<Article> createArticle(Article article);

    Optional<Article> changeArticleById(Long id, Article article);

    Optional<Boolean> removeUserById(Long id);

}
