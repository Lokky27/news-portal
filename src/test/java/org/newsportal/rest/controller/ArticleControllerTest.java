package org.newsportal.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.newsportal.configuration.AppConfiguration;
import org.newsportal.service.ArticleService;
import org.newsportal.service.model.Article;
import org.newsportal.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import  static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.web.servlet.function.ServerResponse.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfiguration.class})
@WebAppConfiguration
class ArticleControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesArticleController() {
        ServletContext context = webApplicationContext.getServletContext();
        Assertions.assertNotNull(context);
        Assertions.assertTrue(context instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("articleController"));
    }

    @Test
    void getAllArticles() throws Exception {
        this.mockMvc.perform(get("/news-portal/articles")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getArticleById() throws Exception {
        this.mockMvc.perform(get("/news-portal/articles/{articleId}", 13L))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createArticle() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/news-portal/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"new article\", \"content\":  \"A content of a new article\"}" )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void changeArticleById() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/news-portal/articles/{articleId}", 11L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"updated article\", \"content\":  \"An updated article content\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeArticleById() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/news-portal/articles/{articleId}", 14L))
                .andExpect(status().isOk());
    }
}