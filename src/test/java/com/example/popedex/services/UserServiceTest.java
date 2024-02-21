package com.example.popedex.services;

import com.example.popedex.TestPopedexApplication;
import com.example.popedex.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
@Import({TestPopedexApplication.class})
@Sql(scripts = "/scripts/users_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void addUser() {
        userService.addUser("name", "password", "email@ema.il", LocalDateTime.now(), "password");
        assertEquals("email@ema.il", userService.getUser("name").email());
    }

    @Test
    void addUserShouldThrowOnWrongRepassword() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> userService.addUser("name", "password", "email", LocalDateTime.now(), "assword"),
                "password should be the same");
    }

    @Test
    void addUserShouldThrowOnUsernameNotUnique() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> userService.addUser("test", "password", "email", LocalDateTime.now(), "password"),
                "username should be unique");
    }

    @Test
    void getUser() {
        User user = userService.getUser("test");
        assertEquals("test@te.st", user.email());
    }

    @Test
    void getUserFromPrincipalUsernamePassword() {
        UsernamePasswordAuthenticationToken token = Mockito.mock(UsernamePasswordAuthenticationToken.class);
        Mockito.when(token.getName()).thenReturn("test");
        User user = userService.getUserFromPrincipal(token);
        assertEquals("test@te.st", user.email());
    }
}