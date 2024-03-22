package com.example.popedex.entities;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Picture(@Id Long id, byte[] picture, Long statueId, Long addedBy, LocalDateTime addedAt) {
}
