package com.example.booking.service;

import com.example.booking.model.Showtime;
import com.example.booking.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public Optional<Showtime> findById(Long id) {
        return showtimeRepository.findById(id);
    }
}
