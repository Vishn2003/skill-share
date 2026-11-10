package com.skillhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillhub.entity.LearningRequest;

@Repository
public interface LearningRequestRepository extends JpaRepository<LearningRequest, Long> {

    List<LearningRequest> findByLearnerStudentId(Long learnerId);

    List<LearningRequest> findByMentorStudentId(Long mentorId);

}