package com.example.ticketsystemspry.service;

import com.example.ticketsystemspry.dto.BookingResponse;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingResponse confirmHold(UUID holdId);
    List<BookingResponse> getBookingsByUser(UUID userId);
    boolean cancelBooking(UUID bookingId, String reason);
}
