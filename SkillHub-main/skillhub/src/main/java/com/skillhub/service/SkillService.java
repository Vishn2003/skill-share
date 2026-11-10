package com.skillhub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillhub.entity.Skill;
import com.skillhub.repository.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill addSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    public List<Skill> searchSkill(String skillName) {
        return skillRepository.findBySkillNameContainingIgnoreCase(skillName);
    }

    public Skill updateSkill(Long id, Skill updatedSkill) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        skill.setSkillName(updatedSkill.getSkillName());
        skill.setCategory(updatedSkill.getCategory());

        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}