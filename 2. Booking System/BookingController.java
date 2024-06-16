package com.example.booking.controller;

import com.example.booking.model.Booking;
import com.example.booking.model.Showtime;
import com.example.booking.model.User;
import com.example.booking.service.BookingService;
import com.example.booking.service.ShowtimeService;
import com.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private UserService userService;

    @GetMapping("/book/{showtimeId}")
    public String bookTickets(@PathVariable Long showtimeId, Model model) {
        Optional<Showtime> showtime = showtimeService.findById(showtimeId);
        if (showtime.isPresent()) {
            model.addAttribute("showtime", showtime.get());
            return "book";
        }
        return "redirect:/movies";
    }

    @PostMapping("/book")
    public String bookTickets(@RequestParam Long showtimeId, @RequestParam String seatNumbers, @RequestParam double totalPrice) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        Showtime showtime = showtimeService.findById(showtimeId).orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id:" + showtimeId));
        Booking booking = new Booking();
        booking.setShowtime(showtime);
        booking.setUser(user);
        booking.setSeatNumbers(seatNumbers);
        booking.setTotalPrice(totalPrice);
        booking.setBookingTime(LocalDateTime.now());

        bookingService.save(booking);
        return "redirect:/confirmation/" + booking.getId();
    }

    @GetMapping("/confirmation/{id}")
    public String confirmation(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
        model.addAttribute("booking", booking);
        return "confirmation";
    }
}
