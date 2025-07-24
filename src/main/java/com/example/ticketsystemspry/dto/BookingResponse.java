package com.example.ticketsystemspry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private UUID bookingId;
    private UUID eventId;
    private UUID userId;
    private int seatCount;
    private LocalDateTime bookedAt;
    private String status;
    private String eventName;
    private LocalDateTime eventDate;
    private String eventLocation;
    private String cancelReason;
}
