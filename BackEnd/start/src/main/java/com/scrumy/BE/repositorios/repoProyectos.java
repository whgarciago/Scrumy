package com.scrumy.BE.repositorios;

import java.util.*;

import com.scrumy.BE.modelos.Proyectos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface repoProyectos extends JpaRepository <Proyectos, Integer> {

    List<Proyectos> findByProyectoID(int Proyectos);

    List<Proyectos> findByidUsuarios(int idUsuario);

}