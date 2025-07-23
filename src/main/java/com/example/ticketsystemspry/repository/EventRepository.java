package com.example.ticketsystemspry.repository;

import com.example.ticketsystemspry.model.Event;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, java.util.UUID> {

    List<Event> findByDeletedFalse();

    Optional<Event> findByIdAndDeletedFalse(UUID id);

    // this lock ensures the concurrent updates to the same event are handled correctly
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Optional<Event> findByIdWithLock(@Param("id") UUID id);
}
