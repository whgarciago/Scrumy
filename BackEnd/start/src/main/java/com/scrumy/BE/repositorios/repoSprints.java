package com.scrumy.BE.repositorios;

import java.util.*;
import com.scrumy.BE.modelos.Sprints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface repoSprints extends JpaRepository <Sprints,Integer> {
    
    List<Sprints> findBySprintID(int Sprints);
    List<Sprints> findByidProyecto(int idProyecto);
}
