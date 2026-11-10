package com.skillhub.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(nullable = false, unique = true)
    private String skillName;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "skill")
    @JsonIgnore
    private List<StudentSkill> studentSkills;

    public Skill() {
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<StudentSkill> getStudentSkills() {
        return studentSkills;
    }

    public void setStudentSkills(List<StudentSkill> studentSkills) {
        this.studentSkills = studentSkills;
    }
}