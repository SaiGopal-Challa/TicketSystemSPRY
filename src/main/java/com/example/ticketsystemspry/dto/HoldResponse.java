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
public class HoldResponse {
    private java.util.UUID holdId;
    private LocalDateTime expiresAt;
}
