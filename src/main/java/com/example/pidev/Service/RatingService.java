package com.example.pidev.Service;
import com.example.pidev.Repository.ProposalRepo;
import com.example.pidev.Repository.RatingRepo;
import com.example.pidev.entity.Proposal;
import com.example.pidev.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RatingService implements IRatingService{
    @Autowired
    RatingRepo ratingRepo;

    ProposalRepo proposalRepo;
    @Override
    public List<Rating> retrieveAllRating() {
        return null;
    }

    @Override
    public Double addRating(Rating r, Integer idProposal) {
        Rating existingRating = ratingRepo.findByProposal_Idproposal(idProposal);
        if(existingRating !=null)
        {
            existingRating.setScore(r.getScore());
            ratingRepo.save(existingRating);
        }
        else {
            Proposal proposal = proposalRepo.findById(idProposal).get();
            r.setProposal(proposal);
            ratingRepo.save(r);
        }
        Proposal proposal = proposalRepo.findById(idProposal).get();
        r.setProposal(proposal);
        Double average = ratingRepo.getAverageRatingForProposal(idProposal);
        proposal.setRate(average);
        proposalRepo.save(proposal);
        return average;
    }

    @Override
    public Rating updateRating(Rating r) {
        return null;
    }

    @Override
    public Rating retrieveRating(Integer idRating) {
        return null;
    }

    @Override
    public void deleteRating(Integer idRating) {

    }
}
