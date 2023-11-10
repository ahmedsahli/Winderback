package com.example.pidev.Repository;

import com.example.pidev.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
    public Role findRoleByRoleName(String roleName);

}