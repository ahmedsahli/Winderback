package com.example.pidev.Controller;

import com.example.pidev.Service.SkillService;
import com.example.pidev.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SkillController {
    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillController skillController;



    // save skill from service
    @GetMapping("/allSkills")
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }
    @PostMapping("/addSkill")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        System.out.println(skill);
        Skill createdSkill = skillService.createSkill(skill);
        return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
    }


    // update skill from service
    @PutMapping("/updateSkill/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Skill updatedSkill = skillService.updateSkill(id, skill);
        if (updatedSkill != null) {
            return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteSkill/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        boolean isDeleted = skillService.deleteSkill(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getSkill/{id}")
    public Optional<Skill> getSkillById(@RequestBody Skill skill) {

        return skillService.getSkillById( skill.getId());
    }


}
