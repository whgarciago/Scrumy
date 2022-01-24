package com.scrumy.BE.controladores;

import java.util.*;

import com.scrumy.BE.modelos.Meta;
import com.scrumy.BE.repositorios.repoMeta;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")

public class controlMeta {
    @Autowired
    repoMeta RM;


    @GetMapping("/metas/all")
    public ResponseEntity<List<Meta>> getAllMetaByProject(@RequestParam int id){

        try{
            List<Meta> listaMeta = new ArrayList<Meta>();
            RM.findByidProyecto(id).forEach(listaMeta::add);
            if(listaMeta.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaMeta, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/metas/find")
    public ResponseEntity<Optional<Meta>> getMetasbyID(@RequestParam int id){
        try{
            Optional<Meta> foundProject = RM.findById(id);
            if(foundProject.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundProject, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/metas/create")
    public ResponseEntity<Meta> createMeta(@RequestBody Meta m){
        try{
            Meta tdb = RM.save(new Meta(m.getNombre(), m.getIdProyecto() , m.getEstado() , m.getIdSprint() , m.getDescripcion(), m.getDificultad()));
            return new ResponseEntity<>(tdb,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/metas/remove")
    public ResponseEntity<HttpStatus> deleteMeta(@RequestParam int id){
        try{
            RM.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/metas/update")
    public ResponseEntity<Meta> updateMeta(@RequestParam int id, @RequestBody Meta updMeta){
        Optional<Meta> metadata = RM.findById(id);
            
        if(metadata.isPresent()){
            Meta m = metadata.get();            
            m.setNombre(updMeta.getNombre());
            m.setIdProyecto(updMeta.getIdProyecto());
            m.setEstado(updMeta.getEstado());
            m.setIdSprint(updMeta.getIdSprint());
            m.setDescripcion(updMeta.getDescripcion());

            return new ResponseEntity<>(RM.save(m),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/metas/updateDificulties")
    public ResponseEntity<Meta> updateDificultyInMeta(@RequestParam int id, @RequestBody Meta updMeta){
        Optional<Meta> metadata = RM.findById(id);
            
        if(metadata.isPresent()){
            Meta m = metadata.get();            
            m.setDificultad(updMeta.getDificultad());

            return new ResponseEntity<>(RM.save(m),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
