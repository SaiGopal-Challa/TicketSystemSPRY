package com.example.ticketsystemspry.config;

import com.example.ticketsystemspry.service.SeatHoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class SeatHoldCleanupScheduler {

    private final SeatHoldService seatHoldService;

    // Running every 1 min
    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredHolds() {
        seatHoldService.expireOldHolds();
    }
}
