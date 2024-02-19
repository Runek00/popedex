package com.example.popedex.services;

import com.example.popedex.TestPopedexApplication;
import com.example.popedex.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import({TestPopedexApplication.class})
@Sql(scripts = "/scripts/users_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void addUser() {
        userService.addUser("name", "password", "email@ema.il", LocalDateTime.now(), true, "password");
        assertEquals("email@ema.il", userService.getUser("name").email());
    }

    @Test
    void addUserShouldThrowOnWrongRepassword() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser("name", "password", "email", LocalDateTime.now(), true, "assword"));
    }

    @Test
    void addUserShouldThrowOnUsernameNotUnique() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser("test", "password", "email", LocalDateTime.now(), true, "password"));
    }

    @Test
    void getUser() {
        User user = userService.getUser("test");
        assertEquals("test@te.st", user.email());
    }
}