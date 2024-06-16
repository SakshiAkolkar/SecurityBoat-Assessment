package com.example.booking.model;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;

    @ElementCollection
    @CollectionTable(name = "screen_seats", joinColumns = @JoinColumn(name = "screen_id"))
    @MapKeyColumn(name = "seat_number")
    @Column(name = "is_available")
    private Map<String, Boolean> seats;

    // Getters and setters
}
