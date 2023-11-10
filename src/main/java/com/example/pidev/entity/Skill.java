package com.example.pidev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




@Entity(name = "skill")
@Data
@NoArgsConstructor

@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @ManyToMany(mappedBy = "skills",cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    // getters and setters
}