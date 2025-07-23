package com.example.ticketsystemspry.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.ticketsystemspry.dto.EventDTO;
import com.example.ticketsystemspry.model.Event;
import com.example.ticketsystemspry.repository.EventRepository;
import com.example.ticketsystemspry.service.EventService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    // this for create event, new event
    @Override
    public EventDTO createEvent(EventDTO dto) {
        Event event = Event.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .date(dto.getDate())
                .location(dto.getLocation())
                .totalSeats(dto.getTotalSeats())
                .deleted(false)
                .build();
        return toDTO(eventRepository.save(event));
    }

    // this for get all event, show all events
    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // this for get one event, show one event by id
    @Override
    public EventDTO getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return toDTO(event);
    }

    // this for update event, change info of event
    @Override
    public EventDTO updateEvent(UUID id, EventDTO dto) {
        Event event = eventRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            event.setName(dto.getName());
        }
        if (dto.getDate() != null && !dto.getDate().toString().trim().isEmpty()) {

            event.setDate(dto.getDate());
        }
        if (dto.getLocation() != null && !dto.getLocation().trim().isEmpty()) {

            event.setLocation(dto.getLocation());
        }
        if (dto.getTotalSeats() != null) {

            event.setTotalSeats(dto.getTotalSeats());
        }

        return toDTO(eventRepository.save(event));
    }

    // this for delete event, not real delete, just mark as deleted
    @Override
    public void deleteEvent(UUID id) {
        Event event = eventRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Event is not found"));
        event.setDeleted(true);
        eventRepository.save(event);
    }

    // this for convert event to dto, make response
    private EventDTO toDTO(Event ev) {
        return EventDTO.builder()
                .id(ev.getId())
                .name(ev.getName())
                .date(ev.getDate())
                .location(ev.getLocation())
                .totalSeats(ev.getTotalSeats())
                .isDeleted(ev.isDeleted())
                .build();
    }
}
