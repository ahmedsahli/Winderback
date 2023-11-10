package com.example.pidev.Repository;

import com.example.pidev.entity.DictionnaireBadWords;
import com.example.pidev.entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DictionnaireRepository extends JpaRepository<DictionnaireBadWords, Long> {



    @Query("SELECT c FROM Reclamation c WHERE c.sendingDate = :d1 ")
    public List<Reclamation> getReclamationBYdate(@Param("d1") Date d1);
}