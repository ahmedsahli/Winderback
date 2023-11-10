package com.example.pidev.Controller;

import com.example.pidev.Service.BadWordService;
import com.example.pidev.entity.DictionnaireBadWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/badword")
public class BadWordController {

    @Autowired
    BadWordService badWordService;


    @PostMapping("/add-badword")
    @ResponseBody
    public void addbadword(@RequestBody DictionnaireBadWords r)
    {
        badWordService.addBadWord(r);

    }


    @GetMapping("/listbadword")
    @ResponseBody
    public List<DictionnaireBadWords> listbadword(){
        return badWordService.retrieveAllBadWord();
    }

    @DeleteMapping("/deletebadword/{idbadword}")
    @ResponseBody
    public void deletebadword(@PathVariable("idbadword") Long idReclamation){
        badWordService.deleteBadWord(idReclamation);
    }
    @PutMapping("/modifierbadword/{idbadword}")
    @ResponseBody
    public void modifierbadword(@RequestBody DictionnaireBadWords r,@PathVariable("idbadword") Long idReclamation){
        badWordService.updateBadWord(r,idReclamation);
    }
}
