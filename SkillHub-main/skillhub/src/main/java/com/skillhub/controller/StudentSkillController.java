package com.skillhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skillhub.entity.StudentSkill;
import com.skillhub.service.StudentSkillService;

@RestController
@RequestMapping("/student-skills")
public class StudentSkillController {

    @Autowired
    private StudentSkillService service;

    @PostMapping
    public StudentSkill addStudentSkill(@RequestBody StudentSkill studentSkill) {
        return service.addStudentSkill(studentSkill);
    }

    @GetMapping
    public List<StudentSkill> getAllStudentSkills() {
        return service.getAllStudentSkills();
    }

    @GetMapping("/student/{studentId}")
    public List<StudentSkill> getSkillsByStudent(@PathVariable Long studentId) {
        return service.getSkillsByStudent(studentId);
    }

    @DeleteMapping("/{id}")
    public String deleteStudentSkill(@PathVariable Long id) {
        service.deleteStudentSkill(id);
        return "Student Skill deleted successfully.";
    }
}