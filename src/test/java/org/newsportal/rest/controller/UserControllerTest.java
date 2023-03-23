package org.newsportal.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.newsportal.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfiguration.class})
@WebAppConfiguration
class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesArticleController() {
        ServletContext context = webApplicationContext.getServletContext();
        Assertions.assertNotNull(context);
        Assertions.assertTrue(context instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("userController"));
    }

    @Test
    void getAllUsers() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/news-portal/users")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUserById() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/news-portal/users/{userId}", 3L)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUserByUsername() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/news-portal/users/username")
                        .param("username", "JohnyD")
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void createUser() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/news-portal/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"AntonyR\", \"password\": \"abcdefgh12345\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void changeUserById() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/news-portal/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Updated PeterG\", \"password\": \"12345qwerty\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeUserById() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/news-portal/users/{userId}", 11L)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}