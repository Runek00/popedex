package com.example.popedex.controllers;

import com.example.popedex.TestPopedexApplication;
import com.example.popedex.entities.Statue;
import com.example.popedex.services.StatueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestPopedexApplication.class})
@Sql(scripts = "/scripts/statue_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@WithMockUser(value = "statue_test", password = "statue_test")
class StatueControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StatueService statueService;


    @Test
    void statuesNewPage() throws Exception {
        mvc.perform(get("/statues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>List of statues</small>")));
    }

    @Test
    void statuesMore() throws Exception {
        mvc.perform(get("/statues").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(not(containsString("<small hidden>List of statues</small>"))));
    }

    @Test
    void myStatues() throws Exception {
        mvc.perform(get("/statues/my"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>List of statues</small>")));
    }

    @Test
    void myStatuesMore() throws Exception {
        mvc.perform(get("/statues/my").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(not(containsString("<small hidden>List of statues</small>"))));
    }

    @Test
    void showExistingStatueInfo() throws Exception {
        mvc.perform(get("/statues/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>Statue view</small>")));
    }

    @Test
    void showNotExistingStatueInfo() throws Exception {
        mvc.perform(get("/statues/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("There is no statue with such name")));
    }

    @Test
    void newStatueForm() throws Exception {
        mvc.perform(get("/statues/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<small hidden>Add statue</small>")));
    }

    @Test
    void addNewStatue() throws Exception {
        Map<String, List<String>> params = new HashMap<>();
        params.put("locationName", List.of("input test location"));
        params.put("unveilingDate", List.of(LocalDate.now().toString()));
        params.put("exists", List.of("true"));
        mvc.perform(post("/statues/new").params(CollectionUtils.toMultiValueMap(params)))
                .andExpect(status().is3xxRedirection());

        List<Statue> statues = statueService.findAllPaginated("input test location", 3, 0);
        assertEquals(1, statues.size());
        Statue statue = statues.getFirst();
        assertEquals(true, statue.exists());
        assertEquals(LocalDate.now(), statue.unveilingDate());
    }
}