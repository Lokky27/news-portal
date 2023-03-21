package org.newsportal.service.mapper.impl;

import org.newsportal.database.repository.UserRepository;
import org.newsportal.database.repository.entity.Article;
import org.newsportal.database.repository.entity.User;
import org.newsportal.database.repository.impl.UserRepositoryImpl;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToDatabase(org.newsportal.service.model.User source) {
        if(source == null) return null;
        Set<Article> articlesToDatabase = new HashSet<>();
        source.getArticleSet().forEach(article -> {
            Article articleToDb = new Article();
            articleToDb.setId(article.getId());
            articleToDb.setTitle(article.getTitle());
            articleToDb.setContent(article.getContent());
            articlesToDatabase.add(articleToDb);
        });

        return new User(source.getId(), source.getUsername(), source.getPassword(), articlesToDatabase);
    }

    @Override
    public org.newsportal.service.model.User mapToService(User source) {
        if (source == null) return null;
        Set<org.newsportal.service.model.Article> articleSet = new HashSet<>();
        source.getArticleSet().forEach(article -> {
            org.newsportal.service.model.Article articleToService = org.newsportal.service.model.Article.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .build();
            articleSet.add(articleToService);
        });

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

    public static void main(String[] args) {
        UserRepository repository = new UserRepositoryImpl();
        UserMapper mapper = new UserMapperImpl();
        User user = repository.findById(3L);
        org.newsportal.service.model.User serviceUser = mapper.mapToService(user);
        System.out.println(serviceUser);

    }
}
