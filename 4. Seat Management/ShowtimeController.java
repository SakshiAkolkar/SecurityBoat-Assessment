package com.example.booking.controller;

import com.example.booking.model.SeatLock;
import com.example.booking.model.Showtime;
import com.example.booking.model.User;
import com.example.booking.service.SeatLockService;
import com.example.booking.service.ShowtimeService;
import com.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private SeatLockService seatLockService;

    @Autowired
    private UserService userService;

    @GetMapping("/showtimes/{id}")
    public String showtimeDetails(@PathVariable Long id, Model model) {
        Showtime showtime = showtimeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id:" + id));
        seatLockService.releaseExpiredLocks(showtime);
        List<Seat
