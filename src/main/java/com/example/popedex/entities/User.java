package com.example.popedex.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "users")
public record User(@Id Long id, String username, String password, String visibleName, String email, LocalDateTime registerTime, boolean enabled, boolean fromOauth) {
    public User(Long id, String username, String password, String email, LocalDateTime registerTime, boolean enabled, boolean fromOauth) {
        this(id, username, password, username, email, registerTime, enabled, fromOauth);
    }
}
