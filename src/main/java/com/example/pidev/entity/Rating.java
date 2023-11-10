package com.example.pidev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "idRating")
    private Integer idRating;
    private Double score;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Proposal proposal;
}

