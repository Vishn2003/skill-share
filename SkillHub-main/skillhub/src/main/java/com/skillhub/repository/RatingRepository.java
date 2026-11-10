package com.skillhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillhub.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByMentorStudentId(Long mentorId);

}