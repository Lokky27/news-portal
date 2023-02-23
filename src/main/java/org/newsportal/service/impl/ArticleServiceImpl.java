package org.newsportal.service.impl;

import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.service.ArticleService;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.Article;

import java.util.List;
import java.util.Optional;

public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public Optional<List<Article>> getAll() {

        return Optional.of(articleMapper.mapToService(articleRepository.findAll()));
    }

    @Override
    public Optional<Article> getById(Long id) {
        return Optional.of(articleMapper.mapToService(articleRepository.findById(id)));
    }

    @Override
    public Optional<Article> getByTitle(String title) {

        return Optional.of(articleMapper.mapToService(articleRepository.findByTitle(title)));
    }

    @Override
    public Optional<Article> createUser(Article article) {
        org.newsportal.database.repository.entity.Article articleToSave = articleMapper.mapToDatabase(article);
        return Optional.of(articleMapper.mapToService(articleRepository.createArticle(articleToSave)));
    }

    @Override
    public Optional<Article> changeArticleById(Long id, Article article) {
        org.newsportal.database.repository.entity.Article articleToUpdate = articleMapper.mapToDatabase(article);
        return Optional.of(articleMapper.mapToService(articleRepository.updateArticleById(id, articleToUpdate)));
    }

    @Override
    public void removeUserById(Long id) {
        articleRepository.deleteArticleById(id);

    }
}
