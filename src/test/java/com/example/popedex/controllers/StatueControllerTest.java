package com.example.popedex.controllers;

import com.example.popedex.TestPopedexApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestPopedexApplication.class})
@Sql(scripts = "/scripts/statue_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@WithMockUser(value = "test", password = "test")
class StatueControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void statuesNewPage() throws Exception {
        mvc.perform(get("/statues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void statuesMore() throws Exception {
        mvc.perform(get("/statues").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void myStatues() throws Exception {
        mvc.perform(get("/statues/my").param("page", "1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void myStatuesMore() throws Exception {
        mvc.perform(get("/statues/my").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void showExistingStatueInfo() throws Exception {
        mvc.perform(get("/statues/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void showNotExistingStatueInfo() throws Exception {
        mvc.perform(get("/statues/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void newStatueInput() {
    }

    @Test
    void addNewStatue() {
    }
}