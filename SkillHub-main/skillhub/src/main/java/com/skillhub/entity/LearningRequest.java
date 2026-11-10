package com.skillhub.entity;

import com.skillhub.enums.RequestStatus;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "learning_requests")
public class LearningRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Student learner;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Student mentor;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(length = 300)
    private String message;

    @OneToOne(mappedBy = "request")
    @JsonIgnore
    private Rating rating;

    public LearningRequest() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Student getLearner() {
        return learner;
    }

    public void setLearner(Student learner) {
        this.learner = learner;
    }

    public Student getMentor() {
        return mentor;
    }

    public void setMentor(Student mentor) {
        this.mentor = mentor;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}