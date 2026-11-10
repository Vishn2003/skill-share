package com.skillhub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillhub.entity.StudentSkill;
import com.skillhub.repository.StudentSkillRepository;

@Service
public class StudentSkillService {

    @Autowired
    private StudentSkillRepository repository;

    public StudentSkill addStudentSkill(StudentSkill studentSkill) {
        return repository.save(studentSkill);
    }

    public List<StudentSkill> getAllStudentSkills() {
        return repository.findAll();
    }

    public List<StudentSkill> getSkillsByStudent(Long studentId) {
        return repository.findByStudentStudentId(studentId);
    }

    public void deleteStudentSkill(Long id) {
        repository.deleteById(id);
    }
}