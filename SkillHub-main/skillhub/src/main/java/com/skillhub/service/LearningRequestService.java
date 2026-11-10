package com.skillhub.service;

import java.util.List;
import com.skillhub.dto.LearningRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillhub.entity.LearningRequest;
import com.skillhub.enums.RequestStatus;
import com.skillhub.repository.LearningRequestRepository;

@Service
public class LearningRequestService {

    @Autowired
    private LearningRequestRepository repository;

    public LearningRequest createRequest(LearningRequest request) {

        request.setStatus(RequestStatus.PENDING);

        return repository.save(request);
    }

    public LearningRequestDTO convertToDTO(LearningRequest request) {

        return new LearningRequestDTO(
                request.getRequestId(),
                request.getLearner().getStudentId(),
                request.getMentor().getStudentId(),
                request.getSkill().getSkillId(),
                request.getStatus(),
                request.getMessage()
        );
    }

    public List<LearningRequest> getAllRequests() {
        return repository.findAll();
    }

    public List<LearningRequestDTO> getAllRequestDTOs() {

        return repository.findAll()
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public List<LearningRequest> getLearnerRequests(Long learnerId) {
        return repository.findByLearnerStudentId(learnerId);
    }

    public List<LearningRequestDTO> getLearnerRequestDTOs(Long learnerId) {

        return repository.findByLearnerStudentId(learnerId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public List<LearningRequest> getMentorRequests(Long mentorId) {
        return repository.findByMentorStudentId(mentorId);
    }

    public List<LearningRequestDTO> getMentorRequestDTOs(Long mentorId) {

        return repository.findByMentorStudentId(mentorId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public LearningRequest updateStatus(Long id, RequestStatus status) {

        LearningRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);

        return repository.save(request);
    }

    public void deleteRequest(Long id) {
        repository.deleteById(id);
    }

}