package com.skillhub.dto;

public class RatingDTO {

    private Long ratingId;
    private Long mentorId;
    private Long learnerId;
    private Long requestId;
    private Integer rating;
    private String feedback;

    public RatingDTO() {
    }

    public RatingDTO(Long ratingId,
                     Long mentorId,
                     Long learnerId,
                     Long requestId,
                     Integer rating,
                     String feedback) {
        this.ratingId = ratingId;
        this.mentorId = mentorId;
        this.learnerId = learnerId;
        this.requestId = requestId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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