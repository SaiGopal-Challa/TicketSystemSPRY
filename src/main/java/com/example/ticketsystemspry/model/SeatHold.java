package com.example.ticketsystemspry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seat_hold")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatHold {

    @Id
    @Column(name = "hold_id")
    private UUID holdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private HoldStatus status;

    public enum HoldStatus {
        HELD,
        EXPIRED,
        CANCELED
    }
}
