package com.skillhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skillhub.entity.Student;
import com.skillhub.entity.Skill;
import com.skillhub.entity.LearningRequest;
import com.skillhub.entity.Rating;
import com.skillhub.repository.StudentRepository;
import com.skillhub.repository.SkillRepository;
import com.skillhub.repository.LearningRequestRepository;
import com.skillhub.repository.RatingRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private LearningRequestRepository learningRequestRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/skills")
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @GetMapping("/requests")
    public List<LearningRequest> getAllRequests() {
        return learningRequestRepository.findAll();
    }

    @GetMapping("/ratings")
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}