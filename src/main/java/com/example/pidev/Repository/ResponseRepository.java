package com.example.pidev.Repository;

import com.example.pidev.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResponseRepository extends JpaRepository<Response, Long>{

    // get reclamation by username7

}
