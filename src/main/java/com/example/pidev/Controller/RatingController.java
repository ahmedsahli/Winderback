package com.example.pidev.Controller;

import com.example.pidev.Service.IRatingService;
import com.example.pidev.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rate")
public class RatingController {
    @Autowired
    IRatingService iRatingService;

    @PostMapping("/addRate/{idProposal}")
    public Double addRating(@RequestBody Rating r, @PathVariable("idProposal") Integer idProposal){
        return iRatingService.addRating(r,idProposal);

    }
}
