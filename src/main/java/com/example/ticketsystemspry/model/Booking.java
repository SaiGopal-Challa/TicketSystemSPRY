package com.example.ticketsystemspry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @Column(name = "booking_id")
    private UUID bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    @Builder.Default
    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "hold_id")
    private SeatHold hold;

    @Column(name = "cancel_reason")
    private String cancelReason;

    public enum BookingStatus {
        CONFIRMED,
        CANCELED
    }
}
