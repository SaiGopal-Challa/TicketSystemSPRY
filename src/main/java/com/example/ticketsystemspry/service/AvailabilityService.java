package com.example.ticketsystemspry.service;

import com.example.ticketsystemspry.dto.AvailabilityResponse;

import java.util.UUID;

public interface AvailabilityService {
    AvailabilityResponse getAvailability(UUID eventId);
}
