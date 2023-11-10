package com.example.pidev.Repository;


import com.example.pidev.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepo extends JpaRepository<Rating, Integer> {
    Rating findByProposal_Idproposal(Integer idproposal);
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.proposal.idproposal = :proposalId")
    Double getAverageRatingForProposal(@Param("proposalId") Integer proposalId);
}
