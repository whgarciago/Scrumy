package com.scrumy.BE.controladores;


import java.util.*;

import com.scrumy.BE.modelos.Proyectos;
import com.scrumy.BE.repositorios.repoProyectos;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class controlProyecto {
    
    @Autowired
    repoProyectos RP;

    @PostMapping("/proyectos/create")
    public ResponseEntity<Proyectos> createProyecto(@RequestBody Proyectos u){
        try{
            Proyectos tdb = RP.save(new Proyectos(u.getNombre(), u.getIdUsuarios(),u.getMotivacion(),u.getIdMeta(),u.getDescripcion(),u.getFechaFin()));
            return new ResponseEntity<>(tdb,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
