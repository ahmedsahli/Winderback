package com.example.pidev.Controller;

import com.example.pidev.Repository.ProposalRepo;
import com.example.pidev.Service.ProposalService;
import com.example.pidev.entity.Proposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/Proposal")
public class ProposalController {
    @Autowired
    ProposalService proposalService;
    @Autowired
    ProposalRepo proposalRepo;
    @GetMapping("/list")
    public List<Proposal> retrieveAllProposal(){
        return proposalService.retrieveAllProposal();
    }

    @PostMapping("/addProp")
    @ResponseBody
    public Proposal addProposal(@RequestBody Proposal p){

        System.out.println(p);
        return proposalService.addProposal(p);
    }

    @PutMapping("/updateProp/{idProposal}")
    public Proposal updateProposal(@RequestBody Proposal p,@PathVariable Integer idProposal){
        return this.proposalRepo.findById(idProposal).map(y->{
            y.setClient(p.getClient());
            y.setStatus(p.getStatus());
            y.setPrice(p.getPrice());
            y.setCover_letter(p.getCover_letter());
            return proposalRepo.save(y);
        }).orElseGet(()->{
            p.setIdproposal(idProposal);
            return proposalRepo.save(p);
        });

    }
    @DeleteMapping("/deleteProp/{idProposal}")
    public void deleteProposal(@PathVariable Integer idProposal){
        proposalService.deleteProposal(idProposal);
    }

    @GetMapping("/filterbyrange")
    public List<Proposal> fpp(@RequestParam(name = "minPrice") float minPrice,@RequestParam(name = "maxPrice") float maxPrice ){
        return proposalService.filterbyrange(minPrice, maxPrice);
    }

}