package com.example.ticketsystemspry.controller;

import com.example.ticketsystemspry.dto.EventDTO;
import com.example.ticketsystemspry.service.EventService;
import com.example.ticketsystemspry.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events/")
@RequiredArgsConstructor
// total 6 endpoints for events management
public class EventController {

    private final EventService eventService;
    private final AvailabilityService availabilityService;

    @PostMapping("createEvent")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO dto) {
        try {
            EventDTO created = eventService.createEvent(dto);
            return ResponseEntity.status(201).body(
                java.util.Map.of(
                    "success", true,
                    "event", created
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @GetMapping("getAllEvents")
    public ResponseEntity<?> getAllEvents() {
        try {
            return ResponseEntity.ok(
                java.util.Map.of(
                    "success", true,
                    "events", eventService.getAllEvents()
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @GetMapping("getEvent/{id}")
    // we don't get deleted events here ( deleted events are only shown in getAllEvents )
    public ResponseEntity<?> getEventById(@PathVariable UUID id) {
        try {
            EventDTO event = eventService.getEventById(id);
            int availableSeats = availabilityService.getAvailability(id).getAvailableSeats();
            return ResponseEntity.ok(
                java.util.Map.of(
                    "success", true,
                    "event", event,
                    "availableSeats", availableSeats
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @PutMapping("updateEvent/{id}")
    // we can leave some fields empty to not update them
    public ResponseEntity<?> updateEvent( @PathVariable UUID id, @RequestBody EventDTO dto)
    {
        try {
            EventDTO updated = eventService.updateEvent(id, dto);
            return ResponseEntity.ok(
                java.util.Map.of(
                    "success", true,
                    "event", updated
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @DeleteMapping("deleteEvent/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok(java.util.Map.of(
                "success", true,
                "message", "Event deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @GetMapping("availability/{eventId}")
    // this is to just get count of available seats for an event
    public ResponseEntity<?> getAvailability(@PathVariable UUID eventId) {
        try {
            return ResponseEntity.ok(
                java.util.Map.of(
                    "success", true,
                    "availability", availabilityService.getAvailability(eventId)
                )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }
}
