package com.example.booking.service;

import com.example.booking.model.Screen;
import com.example.booking.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {
    @Autowired
    private ScreenRepository screenRepository;

    public List<Screen> findAll() {
        return screenRepository.findAll();
    }

    public Screen findById(Long id) {
        return screenRepository.findById(id).orElse(null);
    }

    public Screen save(Screen screen) {
        return screenRepository.save(screen);
    }

    public void deleteById(Long id) {
        screenRepository.deleteById(id);
    }
}
