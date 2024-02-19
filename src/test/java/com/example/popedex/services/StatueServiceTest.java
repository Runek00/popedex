package com.example.popedex.services;

import com.example.popedex.TestPopedexApplication;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import({TestPopedexApplication.class})
@Sql(scripts = "/scripts/statue_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class StatueServiceTest {

    @Autowired
    StatueService statueService;

    @Test
    void countForUser() {
        assertEquals(3, statueService.countForUser(0L));
    }

    @Test
    void countForUserWrong() {
        assertThrows(AssertionFailedError.class, () -> assertEquals(5, statueService.countForUser(1L)));
    }
}