package com.skillhub.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotBlank(message = "Department cannot be empty")
    @Column(nullable = false)
    private String department;

    @Min(value = 1, message = "Year must be a positive integer")
    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<StudentSkill> studentSkills;

    @OneToMany(mappedBy = "learner")
    @JsonIgnore
    private List<LearningRequest> learningRequests;

    public Student() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<StudentSkill> getStudentSkills() {
        return studentSkills;
    }

    public void setStudentSkills(List<StudentSkill> studentSkills) {
        this.studentSkills = studentSkills;
    }

    public List<LearningRequest> getLearningRequests() {
        return learningRequests;
    }
    
    public void setLearningRequests(List<LearningRequest> learningRequests) {
        this.learningRequests = learningRequests;
    }

}