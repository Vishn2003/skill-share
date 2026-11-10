package com.skillhub.dto;

import com.skillhub.enums.RequestStatus;

public class LearningRequestDTO {

    private Long requestId;
    private Long learnerId;
    private Long mentorId;
    private Long skillId;
    private RequestStatus status;
    private String message;

    public LearningRequestDTO() {
    }

    public LearningRequestDTO(Long requestId,
                              Long learnerId,
                              Long mentorId,
                              Long skillId,
                              RequestStatus status,
                              String message) {
        this.requestId = requestId;
        this.learnerId = learnerId;
        this.mentorId = mentorId;
        this.skillId = skillId;
        this.status = status;
        this.message = message;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
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
}