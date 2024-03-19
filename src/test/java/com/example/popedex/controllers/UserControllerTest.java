package com.example.popedex.controllers;

import com.example.popedex.TestPopedexApplication;
import com.example.popedex.entities.User;
import com.example.popedex.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestPopedexApplication.class})
@Sql(scripts = "/scripts/users_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@WithMockUser(value = "test", password = "test")
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;


    @Test
    void usersSecret() throws Exception {
        mvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("secret!"));
    }

    @Test
    void usersNewForm() throws Exception {
        mvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>Add user</small>")));
    }

    @Test
    void usersAddNew() throws Exception {
        mvc.perform(post("/users/new")
                        .param("username", "name")
                        .param("password", "password")
                        .param("repassword", "password")
                        .param("email", "name@te.st")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void usersAddWrongRepassword() throws Exception {
        mvc.perform(post("/users/new")
                        .param("username", "name")
                        .param("password", "password")
                        .param("repassword", "password1")
                        .param("email", "name@te.st")
                )
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>Add user</small>")))
                .andExpect(content().string(containsString("password should be the same")));
    }

    @Test
    void usersAddWrongUsernameExists() throws Exception {
        mvc.perform(post("/users/new")
                        .param("username", "test")
                        .param("password", "password")
                        .param("repassword", "password")
                        .param("email", "name@te.st")
                )
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>Add user</small>")))
                .andExpect(content().string(containsString("username should be unique")));
    }

    @Test
    void showUser() throws Exception {
        mvc.perform(get("/users/view"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>User view</small>")))
                .andExpect(content().string(containsString("test@te.st")));
    }

    @Test
    void getEditUserForm() throws Exception {
        mvc.perform(get("/users/edit"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>User edit form</small>")))
                .andExpect(content().string(containsString("test@te.st")));
    }

    @Test
    @WithMockUser(value = "test2", password = "test2")
    void saveUserChanges() throws Exception {
        mvc.perform(post("/users/edit")
                        .param("username", "test3")
                        .param("email", "test3@te.st"))
                .andExpect(status().isFound());
        User user = userService.getUser("test2");
        assertEquals("test3@te.st", user.email());
        assertEquals("test3", user.visibleName());

    }
}