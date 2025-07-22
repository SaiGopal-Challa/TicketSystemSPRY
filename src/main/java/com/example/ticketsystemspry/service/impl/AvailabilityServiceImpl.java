package com.example.ticketsystemspry.service.impl;

import com.example.ticketsystemspry.dto.AvailabilityResponse;
import com.example.ticketsystemspry.model.Event;
import com.example.ticketsystemspry.model.SeatHold;
import com.example.ticketsystemspry.model.Booking;
import com.example.ticketsystemspry.repository.EventRepository;
import com.example.ticketsystemspry.repository.SeatHoldRepository;
import com.example.ticketsystemspry.repository.BookingRepository;
import com.example.ticketsystemspry.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final EventRepository eventRepo;
    private final SeatHoldRepository holdRepo;
    private final BookingRepository bookingRepo;

    @Override
    public AvailabilityResponse getAvailability(UUID eventId) {
        Event event = eventRepo.findByIdAndDeletedFalse(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        int held = holdRepo.sumSeatsHeld(eventId, SeatHold.HoldStatus.HELD);
        int booked = bookingRepo.sumSeatsBooked(eventId, Booking.BookingStatus.CONFIRMED);
        int available = event.getTotalSeats() - held - booked;

        return AvailabilityResponse.builder()
                .eventId(eventId)
                .availableSeats(Math.max(available, 0))
                .build();
    }
}
