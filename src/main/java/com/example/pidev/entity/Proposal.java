package com.example.pidev.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Proposal implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "idproposal")
    private Integer idproposal;
    private String client;
    private String cover_letter;
    private String status;
    private Float price ;
    private Double rate;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proposal")
    private Set<Rating> ratings;
}

