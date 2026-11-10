package com.skillhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.skillhub.dto.LearningRequestDTO;
import com.skillhub.entity.LearningRequest;
import com.skillhub.enums.RequestStatus;
import com.skillhub.service.LearningRequestService;

@RestController
@RequestMapping("/requests")
public class LearningRequestController {

    @Autowired
    private LearningRequestService service;

    @PostMapping
    public LearningRequest createRequest(@RequestBody LearningRequest request) {
        return service.createRequest(request);
    }

    @GetMapping
    public List<LearningRequestDTO> getAllRequests() {
        return service.getAllRequestDTOs();
    }

    @GetMapping("/learner/{id}")
    public List<LearningRequestDTO> getLearnerRequests(@PathVariable Long id) {
        return service.getLearnerRequestDTOs(id);
    }

    @GetMapping("/mentor/{id}")
    public List<LearningRequestDTO> getMentorRequests(@PathVariable Long id) {
        return service.getMentorRequestDTOs(id);
    }

    @PutMapping("/{id}/status")
    public LearningRequest updateStatus(@PathVariable Long id,
                                        @RequestParam RequestStatus status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteRequest(@PathVariable Long id) {
        service.deleteRequest(id);
        return "Request deleted successfully.";
    }
}