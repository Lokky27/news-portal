package org.newsportal.rest.controller;

import org.newsportal.service.ArticleService;
import org.newsportal.service.model.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("news-portal")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll().orElseThrow(() -> new RuntimeException("Cant get all articles")));
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<Article> getArticleById(@PathVariable("articleId") Long id) {
        return ResponseEntity.ok(articleService.getById(id).orElseThrow(() -> new RuntimeException("Article not exists")));
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {

        return ResponseEntity.ok(articleService.createArticle(article).orElseThrow(() -> new RuntimeException("Can't create an Article")));
    }

    @PutMapping("articles/{articleId}")
    public ResponseEntity<Article> changeArticleById(@PathVariable("articleId") Long id,
                                                     @RequestBody Article article) {

        return ResponseEntity.ok(articleService.changeArticleById(id, article).orElseThrow(() -> new RuntimeException("Article not exists")));
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<?> removeArticleById(@PathVariable("articleId") Long id) {
        articleService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
