package com.example.ticketsystemspry.repository;

import com.example.ticketsystemspry.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);

    // Sum of confirmed bookings per event ( we can also use this to get canceled bookings count for audit )
    default int sumSeatsBooked(UUID eventId, Booking.BookingStatus status) {
        return this.findAll().stream()
                .filter(b -> b.getEvent().getId().equals(eventId) && b.getStatus() == status)
                .mapToInt(Booking::getSeatCount)
                .sum();
    }
}
