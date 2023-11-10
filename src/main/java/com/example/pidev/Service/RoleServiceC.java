package com.example.pidev.Service;

import com.example.pidev.Repository.RoleRepository;
import com.example.pidev.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceC {

    @Autowired
    RoleRepository roleRepository;
    public Role createNewRole(Role role) {

        return roleRepository.save(role);
    }

    public List<Role> getAllRole(){return roleRepository.findAll();}


}