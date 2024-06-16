package com.example.booking.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime showtime;
    
    @ManyToOne
    private Movie movie;

    @ElementCollection
    @CollectionTable(name = "seat_availability", joinColumns = @JoinColumn(name = "showtime_id"))
    @MapKeyColumn(name = "seat_number")
    @Column(name = "is_available")
    private Map<String, Boolean> seats;

    // Getters and setters
}
