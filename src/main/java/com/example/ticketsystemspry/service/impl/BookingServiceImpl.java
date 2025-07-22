package com.example.ticketsystemspry.service.impl;

import com.example.ticketsystemspry.dto.BookingResponse;
import com.example.ticketsystemspry.model.Booking;
import com.example.ticketsystemspry.model.SeatHold;
import com.example.ticketsystemspry.repository.BookingRepository;
import com.example.ticketsystemspry.repository.SeatHoldRepository;
import com.example.ticketsystemspry.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final SeatHoldRepository holdRepo;

    @Override
    @Transactional
    public BookingResponse confirmHold(UUID holdId) {
        SeatHold hold = holdRepo.findById(holdId)
                .orElseThrow(() -> new RuntimeException("Hold not found"));

        if (hold.getStatus() != SeatHold.HoldStatus.HELD ||
                hold.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new RuntimeException("Hold expired or not valid");
        }

        Booking booking = Booking.builder()
                .bookingId(UUID.randomUUID())
                .event(hold.getEvent())
                .userId(UUID.fromString(hold.getUserId().toString()))
                .seatCount(hold.getSeatCount())
                .bookedAt(LocalDateTime.now())
                .status(Booking.BookingStatus.CONFIRMED)
                .hold(hold)
                .build();

        hold.setStatus(SeatHold.HoldStatus.EXPIRED);
        holdRepo.save(hold);
        bookingRepo.save(booking);

        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .eventId(booking.getEvent().getId())
                .userId(booking.getUserId())
                .seatCount(booking.getSeatCount())
                .bookedAt(booking.getBookedAt())
                .status(booking.getStatus().name())
                .eventName(booking.getEvent().getName())
                .eventDate(booking.getEvent().getDate())
                .eventLocation(booking.getEvent().getLocation())
                .build();
    }

    @Override
    public List<BookingResponse> getBookingsByUser(UUID userId) {
        return bookingRepo.findByUserId(userId).stream()
                .map(b -> BookingResponse.builder()
                        .bookingId(b.getBookingId())
                        .eventId(b.getEvent().getId())
                        .userId(b.getUserId())
                        .seatCount(b.getSeatCount())
                        .bookedAt(b.getBookedAt())
                        .status(b.getStatus().name())
                        .eventName(b.getEvent().getName())
                        .eventDate(b.getEvent().getDate())
                        .eventLocation(b.getEvent().getLocation())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(UUID bookingId, String reason) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElse(null);
        if (booking == null) return false;
        booking.setStatus(Booking.BookingStatus.CANCELED);
        // Optionally store the reason somewhere if you add a field for it
        bookingRepo.save(booking);
        return true;
    }
}
