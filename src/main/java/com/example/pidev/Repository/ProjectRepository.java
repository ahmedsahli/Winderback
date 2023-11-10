package com.example.pidev.Repository;
import com.example.pidev.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>{

    // count all projects
    Long countBy();

    // project by user email
    List<Project> findByUserEmail(String email);
}
