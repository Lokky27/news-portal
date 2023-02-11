package org.newsportal.database.dao.entity;

import lombok.Data;

@Data
public class Article {
    private Long id;
    private String title;
    private String content;
    private Long user_id;
}
