package com.example.popedex.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(schema = "statue_info")
public record Statue(@Id Long id, String locationName, LocalDate unveilingDate, Boolean exists,
                     @Column("id") User addedBy, Boolean active) {
}
