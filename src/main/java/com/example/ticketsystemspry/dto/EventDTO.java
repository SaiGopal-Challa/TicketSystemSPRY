package com.example.ticketsystemspry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
    private java.util.UUID id;
    private String name;
    private LocalDateTime date;
    private String location;
    private Integer totalSeats;
    private Boolean isDeleted;
}
