package com.example.pidev.Service;

import com.example.pidev.Repository.SkillRepository;
import com.example.pidev.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

   public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional <Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skill) {
        Optional<Skill> existingSkill = skillRepository.findById(id);
        if (existingSkill.isPresent()) {
            skill.setId(id);
            return skillRepository.save(skill);
        } else {
            return null;
        }
    }

    public boolean deleteSkill(Long id) {
        Optional<Skill> existingSkill = skillRepository.findById(id);
        if (existingSkill.isPresent()) {
            skillRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
