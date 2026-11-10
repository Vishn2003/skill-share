package com.skillhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skillhub.entity.Rating;
import com.skillhub.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService service;

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return service.addRating(rating);
    }

    @GetMapping
    public List<Rating> getAllRatings() {
        return service.getAllRatings();
    }

    @GetMapping("/mentor/{mentorId}")
    public List<Rating> getRatingsByMentor(@PathVariable Long mentorId) {
        return service.getRatingsByMentor(mentorId);
    }

    @DeleteMapping("/{id}")
    public String deleteRating(@PathVariable Long id) {
        service.deleteRating(id);
        return "Rating deleted successfully.";
    }
}