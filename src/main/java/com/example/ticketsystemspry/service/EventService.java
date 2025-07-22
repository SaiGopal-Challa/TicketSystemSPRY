package com.example.ticketsystemspry.service;

import com.example.ticketsystemspry.dto.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO createEvent(EventDTO dto);
    List<EventDTO> getAllEvents();
    EventDTO getEventById(java.util.UUID eventId);
    EventDTO updateEvent(java.util.UUID eventId, EventDTO dto);
    void deleteEvent(java.util.UUID eventId);
}
