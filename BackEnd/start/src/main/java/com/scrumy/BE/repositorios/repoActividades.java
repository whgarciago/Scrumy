package com.scrumy.BE.repositorios;

import java.util.*;

import com.scrumy.BE.modelos.Actividades;

import org.springframework.data.jpa.repository.JpaRepository;

public interface repoActividades extends JpaRepository<Actividades,Integer>{
    Optional<Actividades> findByActividadID(int ActividadID);
}
