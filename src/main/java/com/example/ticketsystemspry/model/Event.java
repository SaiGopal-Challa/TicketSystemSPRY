package com.example.ticketsystemspry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;
}
