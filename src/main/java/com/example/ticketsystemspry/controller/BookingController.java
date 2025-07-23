package com.example.ticketsystemspry.controller;

import com.example.ticketsystemspry.dto.ConfirmBookingRequest;
import com.example.ticketsystemspry.dto.BookingResponse;
import com.example.ticketsystemspry.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings/")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("confirmBooking/{eventId}")
    public ResponseEntity<?> confirmBooking(
            @PathVariable UUID eventId, @RequestBody ConfirmBookingRequest request) {
        try {
            BookingResponse resp = bookingService.confirmHold(request.getHoldId());

            return ResponseEntity.status(201).body(
                java.util.Map.of(
                    "success", true,
                    "booking", resp
                )
            );
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @GetMapping("getBookings/{userId}")
    public ResponseEntity<?> getBookingsByUser(@PathVariable UUID userId) {
        try {
            List<BookingResponse> bookings = bookingService.getBookingsByUser(userId);
            return ResponseEntity.ok(
                java.util.Map.of(
                    "success", true,
                    "bookings", bookings
                )
            );
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @DeleteMapping("cancelBooking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable UUID bookingId, @RequestBody(required = false) java.util.Map<String, String> body) {
        try {
            String reason = body != null ? body.getOrDefault("reason", null) : null;
            bookingService.cancelBooking(bookingId, reason);
            return ResponseEntity.ok(java.util.Map.of(
                "success", true,
                "message", "Booking cancelled successfully"
            ));
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }
}
