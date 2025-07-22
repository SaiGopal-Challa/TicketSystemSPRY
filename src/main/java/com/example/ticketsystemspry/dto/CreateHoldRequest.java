package com.example.ticketsystemspry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHoldRequest {
    private java.util.UUID userId;
    private int seatCount;
}
