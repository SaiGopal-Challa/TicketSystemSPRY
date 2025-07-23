package com.example.ticketsystemspry.service;

import com.example.ticketsystemspry.dto.EventDTO;

import java.util.List;
import java.util.UUID;

public interface EventService {
    EventDTO createEvent(EventDTO dto);
    List<EventDTO> getAllEvents();
    EventDTO getEventById(UUID eventId);
    EventDTO updateEvent(UUID eventId, EventDTO dto);
    void deleteEvent(UUID eventId);
}
