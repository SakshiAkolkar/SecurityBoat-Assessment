package com.example.booking.controller;

import com.example.booking.model.Movie;
import com.example.booking.model.Showtime;
import com.example.booking.service.MovieService;
import com.example.booking.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping("/movies")
    public String listMovies(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String showMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
        model.addAttribute("movie", movie);
        return "movie";
    }

    @GetMapping("/showtimes/{id}")
    public String showShowtime(@PathVariable Long id, Model model) {
        Showtime showtime = showtimeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid showtime Id:" + id));
        model.addAttribute("showtime", showtime);
        return "showtime";
    }
}
