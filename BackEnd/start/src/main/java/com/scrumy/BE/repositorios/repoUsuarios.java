package com.scrumy.BE.repositorios;

import java.util.*;

import com.scrumy.BE.modelos.Usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

public interface repoUsuarios extends JpaRepository <Usuarios, Integer>{
    List<Usuarios> findByUsuarioID(int UsuarioID);
}
