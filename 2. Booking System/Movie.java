package com.example.booking.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;

    // Getters and setters
}
