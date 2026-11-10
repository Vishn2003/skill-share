package com.skillhub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skillhub.entity.Skill;
import com.skillhub.service.SkillService;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping
    public Skill addSkill(@RequestBody Skill skill) {
        return skillService.addSkill(skill);
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public Optional<Skill> getSkillById(@PathVariable Long id) {
        return skillService.getSkillById(id);
    }

    @GetMapping("/search")
    public List<Skill> searchSkill(@RequestParam String name) {
        return skillService.searchSkill(name);
    }

    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable Long id,
                             @RequestBody Skill skill) {
        return skillService.updateSkill(id, skill);
    }

    @DeleteMapping("/{id}")
    public String deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return "Skill deleted successfully.";
    }
}