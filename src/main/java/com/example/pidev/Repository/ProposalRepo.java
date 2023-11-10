package com.example.pidev.Repository;

import com.example.pidev.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProposalRepo extends JpaRepository <Proposal, Integer> {

    List<Proposal> findAllByPriceIsBetween(float a, float b);
}
