package com.example.ticketsystemspry.service.impl;

import com.example.ticketsystemspry.dto.HoldResponse;
import com.example.ticketsystemspry.model.Booking;
import com.example.ticketsystemspry.model.Event;
import com.example.ticketsystemspry.model.SeatHold;
import com.example.ticketsystemspry.repository.EventRepository;
import com.example.ticketsystemspry.repository.SeatHoldRepository;
import com.example.ticketsystemspry.repository.BookingRepository;
import com.example.ticketsystemspry.service.SeatHoldService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatHoldServiceImpl implements SeatHoldService {

    private final EventRepository eventRepo;
    private final SeatHoldRepository holdRepo;
    private final BookingRepository bookingRepo;

    // this for hold seat, user want seat hold
    @Override
    @Transactional
    public HoldResponse holdSeats(UUID eventId, UUID userId, int seatCount) {
        Event event = eventRepo.findByIdWithLock(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (event.isDeleted()) {
            throw new RuntimeException("Cannot hold seats for a deleted event");
        }

        if (holdRepo.existsByEventIdAndUserIdAndStatus(eventId, userId, SeatHold.HoldStatus.HELD)) {
            throw new RuntimeException("User already has active hold for this event");
        }

        int held = holdRepo.sumSeatsHeld(eventId, SeatHold.HoldStatus.HELD);
        int booked = bookingRepo.sumSeatsBooked(eventId, Booking.BookingStatus.CONFIRMED);
        int available = event.getTotalSeats() - held - booked;

        if (seatCount > available) {
            throw new RuntimeException("Not enough seats available");
        }

        SeatHold hold = SeatHold.builder()
                .holdId(UUID.randomUUID())
                .event(event)
                .userId(userId)
                .seatCount(seatCount)
                .createdAt(LocalDateTime.now())
                .status(SeatHold.HoldStatus.HELD)
                .build();

        holdRepo.save(hold);

        return HoldResponse.builder()
                .holdId(hold.getHoldId())
                .expiresAt(hold.getCreatedAt().plusMinutes(5))
                .build();
    }

    // this is for cancel hold, user want to cancle the hold now
    @Override
    public void cancelHold(UUID holdId) {
        SeatHold hold = holdRepo.findById(holdId)
                .orElseThrow(() -> new RuntimeException("Hold not found"));
        if (hold.getStatus() == SeatHold.HoldStatus.HELD) {
            hold.setStatus(SeatHold.HoldStatus.CANCELED);
            holdRepo.save(hold);
        } else {
            throw new RuntimeException("Hold is not active");
        }
    }

    // this is for expire old hold, time passed expiry, seat to be freed
    @Override
    public void expireOldHolds() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);
        List<SeatHold> expired = holdRepo.findByStatusAndCreatedAtBefore(
                SeatHold.HoldStatus.HELD, threshold);
        expired.forEach(h -> h.setStatus(SeatHold.HoldStatus.EXPIRED));
        holdRepo.saveAll(expired);
    }
}
