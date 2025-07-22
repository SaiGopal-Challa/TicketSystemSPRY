package com.example.ticketsystemspry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityResponse {
    private java.util.UUID eventId;
    private int availableSeats;
}
