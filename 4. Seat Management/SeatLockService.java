package com.example.booking.service;

import com.example.booking.model.SeatLock;
import com.example.booking.model.Showtime;
import com.example.booking.model.User;
import com.example.booking.repository.SeatLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SeatLockService {
    @Autowired
    private SeatLockRepository seatLockRepository;

    private static final long LOCK_DURATION = 10; // Duration in minutes

    public SeatLock lockSeat(User user, Showtime showtime, String seatNumber) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plusMinutes(LOCK_DURATION);
        SeatLock seatLock = new SeatLock();
        seatLock.setUser(user);
        seatLock.setShowtime(showtime);
        seatLock.setSeatNumber(seatNumber);
        seatLock.setLockTime(now);
        seatLock.setExpiryTime(expiryTime);
        return seatLockRepository.save(seatLock);
    }

    public void releaseSeat(Showtime showtime, String seatNumber) {
        SeatLock seatLock = seatLockRepository.findByShowtimeAndSeatNumber(showtime, seatNumber);
        if (seatLock != null) {
            seatLockRepository.delete(seatLock);
        }
    }

    public List<SeatLock> getActiveLocks(Showtime showtime) {
        return seatLockRepository.findByShowtimeAndExpiryTimeAfter(showtime, LocalDateTime.now());
    }

    public void releaseExpiredLocks(Showtime showtime) {
        List<SeatLock> activeLocks = getActiveLocks(showtime);
        LocalDateTime now = LocalDateTime.now();
        for (SeatLock lock : activeLocks) {
            if (lock.getExpiryTime().isBefore(now)) {
                seatLockRepository.delete(lock);
            }
        }
    }
}
