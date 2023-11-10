package com.example.pidev.Service;

import com.example.pidev.Repository.ProposalRepo;
import com.example.pidev.entity.Proposal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class ProposalService implements IProposalService {
    @Autowired
    ProposalRepo proposalRepo;

    @Override
    public List<Proposal> retrieveAllProposal() {
        return proposalRepo.findAll();
    }

    @Override
    public Proposal addProposal(Proposal p) {
        return proposalRepo.save(p);
    }

    @Override
    public Proposal retrieveProposal(Integer idProposal) {
        return proposalRepo.findById(idProposal).get();
    }

    @Override
    public Proposal updateProposal(Proposal p) {
        return proposalRepo.save(p);
    }

    @Override
    public void deleteProposal(Integer idProposal) {
        proposalRepo.deleteById(idProposal);
    }

    @Override
    public List<Proposal> filterbyrange(float minPrice, float maxPrice) {
        return proposalRepo.findAllByPriceIsBetween(minPrice,maxPrice);
    }


}
