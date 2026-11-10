package com.skillhub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillhub.entity.Rating;
import com.skillhub.repository.RatingRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository repository;

    public Rating addRating(Rating rating) {
        return repository.save(rating);
    }

    public List<Rating> getAllRatings() {
        return repository.findAll();
    }

    public List<Rating> getRatingsByMentor(Long mentorId) {
        return repository.findByMentorStudentId(mentorId);
    }

    public void deleteRating(Long id) {
        repository.deleteById(id);
    }
}