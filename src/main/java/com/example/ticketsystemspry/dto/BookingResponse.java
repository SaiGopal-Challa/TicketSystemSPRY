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
public class BookingResponse {
    private java.util.UUID bookingId;
    private java.util.UUID eventId;
    private java.util.UUID userId;
    private int seatCount;
    private LocalDateTime bookedAt;
    private String status;
    private String eventName;
    private LocalDateTime eventDate;
    private String eventLocation;
}
