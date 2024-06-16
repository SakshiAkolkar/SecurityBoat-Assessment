package com.example.booking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime showtime;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;

    // Getters and setters
}
