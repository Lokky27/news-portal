package org.newsportal.service.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private Set<Article> articleSet;
}
