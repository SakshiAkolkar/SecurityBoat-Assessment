package com.example.booking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SeatLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    @ManyToOne
    private Showtime showtime;

    @ManyToOne
    private User user;

    private LocalDateTime lockTime;
    private LocalDateTime expiryTime;

    // Getters and setters
}
