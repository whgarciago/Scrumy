package com.scrumy.BE.repositorios;

import java.util.List;

import com.scrumy.BE.modelos.Meta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface repoMeta extends JpaRepository <Meta, Integer>{
    List<Meta> findByMetaID(int Meta);
    List<Meta> findByidSprint(int idSprint);
    List<Meta> findByidProyecto(int idProyecto);

    

}
