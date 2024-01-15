package com.example.popedex.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import java.time.LocalDate;

public record Statue(@Id Long id, Point location, String locationName, LocalDate unveilingDate, boolean exists, User addedBy,
              boolean active) {
}
