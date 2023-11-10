package com.example.pidev.Repository;

import com.example.pidev.entity.Role;
import com.example.pidev.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    public Optional<User> findByEmail(String email);
    Page<User> findByRole(Role role, Pageable pageable);
    public User findByToken(String token);
    public boolean existsByEmail(String email);
    @Query(value = "SELECT role1, COUNT(*) FROM user GROUP BY role1", nativeQuery = true)
    List<Object[]> countUsersByRole();


}
