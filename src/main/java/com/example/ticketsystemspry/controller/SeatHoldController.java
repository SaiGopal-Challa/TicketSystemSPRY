package com.example.ticketsystemspry.controller;

import com.example.ticketsystemspry.dto.CreateHoldRequest;
import com.example.ticketsystemspry.dto.HoldResponse;
import com.example.ticketsystemspry.service.SeatHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/events/")
@RequiredArgsConstructor
public class SeatHoldController {

    private final SeatHoldService seatHoldService;

    @PostMapping("holdSeats/{eventId}")
    public ResponseEntity<?> holdSeats(
            @PathVariable UUID eventId, @RequestBody CreateHoldRequest request) {
        try {
            HoldResponse resp = seatHoldService.holdSeats(eventId, request.getUserId(), request.getSeatCount());
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @DeleteMapping("cancelHold/{holdId}")
    public ResponseEntity<?> cancelHold(@PathVariable UUID holdId) {
        try {
            seatHoldService.cancelHold(holdId);
            return ResponseEntity.ok(java.util.Map.of(
                "success", true,
                "message", "Hold cancelled successfully"
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
}
