package org.newsportal.service.mapper;

import org.newsportal.database.repository.entity.User;
import org.newsportal.service.model.Article;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    User mapToDatabase(org.newsportal.service.model.User source);

    org.newsportal.service.model.User mapToService(User source);

    List<User> mapToDatabase(List<org.newsportal.service.model.User> source);

    List<org.newsportal.service.model.User> mapToService(List<User> source);
}
