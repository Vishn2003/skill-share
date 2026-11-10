package com.skillhub.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Student mentor;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Student learner;

    @OneToOne
    @JoinColumn(name = "request_id")
    @JsonIgnore
    private LearningRequest request;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 500)
    private String feedback;

    public Rating() {
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Student getMentor() {
        return mentor;
    }

    public void setMentor(Student mentor) {
        this.mentor = mentor;
    }

    public Student getLearner() {
        return learner;
    }

    public void setLearner(Student learner) {
        this.learner = learner;
    }

    public LearningRequest getRequest() {
        return request;
    }

    public void setRequest(LearningRequest request) {
        this.request = request;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}