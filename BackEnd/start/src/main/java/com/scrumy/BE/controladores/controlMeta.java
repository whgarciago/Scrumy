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
    
    @GetMapping("/metas/findBySprint")
    public ResponseEntity<List<Meta>> getAllMetaBySprint(@RequestParam int id){

        try{
            List<Meta> listaMeta = new ArrayList<Meta>();
            RM.findByidSprint(id).forEach(listaMeta::add);
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

    @GetMapping("/metas/findDificulty")
    public ResponseEntity<String> getDificultyByMeta(@RequestParam int id){
        try{
            Optional<Meta> foundMeta = RM.findById(id);
            if(foundMeta.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Meta m = foundMeta.get();
            return new ResponseEntity<>(m.getDificultad(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/metas/create")
    public ResponseEntity<Meta> createMeta(@RequestBody Meta m){
        try{
            Meta tdb = RM.save(new Meta(m.getNombre(), m.getIdProyecto() , m.getEstado() , m.getIdSprint() , m.getDescripcion(), m.getActividadID(), m.getDificultad()));
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
            m.setActividadID(updMeta.getActividadID());

            return new ResponseEntity<>(RM.save(m),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/metas/update/sprint")
    public ResponseEntity<Meta> updateMetaSprint(@RequestParam int id, int sprintID){
        Optional<Meta> metadata = RM.findById(id);

        if(metadata.isPresent()){
            Meta m = metadata.get();
            m.setIdSprint(sprintID);
            //m.setActividadID(updMeta.getActividadID());

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

    @DeleteMapping("/metas/delete/all")
    public ResponseEntity<HttpStatus> deleteAllMeta(){
        try{
            RM.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
