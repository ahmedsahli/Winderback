package com.example.pidev.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    private String userName;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String fileName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Reclamation> reclamations;
    @Enumerated(EnumType.STRING)
    private Role1 role1 ;

    public Role1 getRole1() {
        return role1;
    }

    public void setRole1(Role1 role) {
        this.role1 = role;
    }

    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Project> projets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;



    public User() {

    }


    public String getToken() {
        return token;
    }

    public void setToken(String Token) {
        this.token = Token;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getRole() {
        return roles;
    }

    public void setRole(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public User(String userName, String nom, String prenom, String password, String email, String fileName, Gender gender, int phoneNumber, Role1 role) {
        this.userName = userName;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.fileName = fileName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.role1 = role;
    }


}

