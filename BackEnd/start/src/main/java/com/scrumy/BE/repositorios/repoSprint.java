package com.scrumy.BE.repositorios;
import com.scrumy.BE.modelos.Sprint;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface repoSprint extends JpaRepository <Sprint, Integer>{
    
    List<Sprint> findBySprintID(int Sprint);
}
