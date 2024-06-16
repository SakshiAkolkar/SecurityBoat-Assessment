package com.example.booking.controller;

import com.example.booking.model.Screen;
import com.example.booking.model.SeatLock;
import com.example.booking.model.Showtime;
import com.example.booking.model.User;
import com.example.booking.service.ScreenService;
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
    private ScreenService screenService;

    @Autowired
    private SeatLockService seatLockService;

    @Autowired
    private UserService userService;

    @GetMapping("/showtimes/{id}")
    public String showtimeDetails(@PathVariable Long id, Model model) {
        Showtime showtime = showtimeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id:" + id));
        seatLockService.releaseExpiredLocks(showtime);
        List<SeatLock> activeLocks = seatLockService.getActiveLocks(showtime);
        Map<String, Boolean> seats = showtime.getScreen().getSeats();
        for (SeatLock lock : activeLocks) {
            seats.put(lock.getSeatNumber(), false);
        }
        model.addAttribute("showtime", showtime);
        model.addAttribute("seats", seats);
        return "showtime";
    }

    @PostMapping("/showtimes/{id}/lock")
    public String lockSeat(@PathVariable Long id, @RequestParam String seatNumber) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Showtime showtime = showtimeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id:" + id));

        if (showtime.getScreen().getSeats().get(seatNumber)) {
            seatLockService.lockSeat(user, showtime, seatNumber);
        }

        return "redirect:/showtimes/" + id;
    }

    @PostMapping("/showtimes/{id}/release")
    public String releaseSeat(@PathVariable Long id, @RequestParam String seatNumber) {
        Showtime showtime = showtimeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id:" + id));
        seatLockService.releaseSeat(showtime, seatNumber);
        return "redirect:/showtimes/" + id;
    }

    @GetMapping("/screens")
    public String listScreens(Model model) {
        List<Screen> screens = screenService.findAll();
        model.addAttribute("screens", screens);
        return "screens";
    }

    @GetMapping("/screens/{id}")
    public String screenDetails(@PathVariable Long id, Model model) {
        Screen screen = screenService.findById(id);
        if (screen == null) {
            return "redirect:/screens";
        }
        model.addAttribute("screen", screen);
        return "screen";
    }
}
