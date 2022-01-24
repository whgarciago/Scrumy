package com.scrumy.BE.controladores;

import java.util.*;

import com.scrumy.BE.modelos.Sprints;
import com.scrumy.BE.repositorios.repoSprints;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

//removido para poder acceder localmente desde cualquier ip
//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class controlSprints {
    
    @Autowired
    repoSprints RS;


    @GetMapping("/sprints/all")
    public ResponseEntity<List<Sprints>> getAllSprintsbyProyectoID (@RequestParam int id){
        try{
            List<Sprints> listaSprints = new ArrayList<Sprints>();
            RS.findByidProyecto(id).forEach(listaSprints::add);
            if(listaSprints.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaSprints,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sprints/find")
    public ResponseEntity<Optional<Sprints>> getSprintbyID(@RequestParam int id){
        try{
            Optional<Sprints> foundSprints = RS.findById(id);
            if(foundSprints.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundSprints, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sprints/create")
    public ResponseEntity<Sprints> createSprint(@RequestBody Sprints s){
        try{
            Sprints tdb = RS.save(new Sprints(/*s.getIdMetas(),*/s.getidProyecto(),s.getFechaInicio(),s.getFechaFinalizacion()));
            return new ResponseEntity<>(tdb,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //comentario para poder hacer el commit

    @DeleteMapping("/sprints/remove")
    public ResponseEntity<HttpStatus> deleteSprint(@RequestParam int id){
        try{
            RS.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sprints/update")
    public ResponseEntity<Sprints> updateProyecto(@RequestParam int id, @RequestBody Sprints updSprints){
        Optional<Sprints> Sprintdata = RS.findById(id);
            
        if(Sprintdata.isPresent()){
            Sprints s = Sprintdata.get();  
            //s.setIdMetas(updSprints.getIdMetas());
            s.setidProyecto(updSprints.getidProyecto());
            s.setFechaInicio(updSprints.getFechaInicio());
            s.setFechaFinalizacion(updSprints.getFechaFinalizacion());          

            return new ResponseEntity<>(RS.save(s),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
