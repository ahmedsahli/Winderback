package com.example.pidev.Service;

import com.example.pidev.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProposalService {
    List<Proposal> retrieveAllProposal();
    Proposal addProposal(Proposal p);
    Proposal retrieveProposal (Integer idProposal);
    Proposal updateProposal (Proposal p);
    void deleteProposal (Integer idProposal);
    List<Proposal> filterbyrange(float minPrice , float maxPrice);

}