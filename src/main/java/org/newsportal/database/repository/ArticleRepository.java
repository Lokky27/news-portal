package org.newsportal.database.repository;

import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;

import java.util.List;

public interface ArticleRepository {
    List<Article> findAll();

    Article findById(Long id);

    Article findByTitle(String title);

    Article createArticle(Article article);

    Article updateArticleById(Long id, Article article);

    void deleteArticleById(Long id);
}
