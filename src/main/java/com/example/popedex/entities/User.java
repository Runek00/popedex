package com.example.popedex.entities;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record User(@Id Long id, String login, String email, LocalDateTime registerTime, boolean active) {
}
