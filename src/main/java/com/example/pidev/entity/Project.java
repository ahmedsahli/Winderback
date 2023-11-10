package com.example.pidev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Scope scope;
    private String duration;
    private String experience;

    private int budgetTo;
    private int budgetFrom;

    @Column( length = 10000)
    private String description;

    @CreationTimestamp
    private Date createdAt;



    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
  //  @ManyToOne
    //@JoinColumn(name = "user_id")
    //private User user;


}
