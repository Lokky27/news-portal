package org.newsportal.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long id;
    private String title;
    private String content;
    private User user;
}
