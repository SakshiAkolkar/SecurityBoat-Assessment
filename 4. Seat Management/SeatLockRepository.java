package com.example.booking.repository;

import com.example.booking.model.SeatLock;
import com.example.booking.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {
    List<SeatLock> findByShowtimeAndExpiryTimeAfter(Showtime showtime, LocalDateTime currentTime);
    SeatLock findByShowtimeAndSeatNumber(Showtime showtime, String seatNumber);
}
