package com.example.ticketsystemspry.repository;

import com.example.ticketsystemspry.model.SeatHold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SeatHoldRepository extends JpaRepository<SeatHold, UUID> {
    boolean existsByEventIdAndUserIdAndStatus(UUID eventId, UUID userId, SeatHold.HoldStatus status);
    List<SeatHold> findByStatusAndCreatedAtBefore(SeatHold.HoldStatus status, LocalDateTime time);

    // Sum for seatCount of active holds
    default int sumSeatsHeld(UUID eventId, SeatHold.HoldStatus status) {
        return this.findAll().stream()
                .filter(h -> h.getEvent().getId().equals(eventId) && h.getStatus() == status)
                .mapToInt(SeatHold::getSeatCount)
                .sum();
    }
}
