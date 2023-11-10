package com.example.pidev.Service;

import com.example.pidev.entity.Rating;

import java.util.List;

public interface IRatingService {
    List<Rating> retrieveAllRating();

    Double addRating (Rating r,Integer idProposal);

    Rating updateRating (Rating r);

    Rating retrieveRating(Integer idRating);



    void deleteRating(Integer idRating);
}