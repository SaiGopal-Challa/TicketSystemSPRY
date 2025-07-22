package com.example.ticketsystemspry.service;

import com.example.ticketsystemspry.dto.HoldResponse;

import java.util.UUID;

public interface SeatHoldService {
    HoldResponse holdSeats(UUID eventId, UUID userId, int seatCount);
    void cancelHold(UUID holdId);
    void expireOldHolds();  // scheduled task
}
