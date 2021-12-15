package com.scrumy.BE.controladores;

import java.util.*;

import com.scrumy.BE.modelos.Sprint;
import com.scrumy.BE.repositorios.repoSprint;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

//Inhabilitado por pruebas, habilitar para permitir conexiones IP
@CrossOrigin(origins = "http://localhost:8081")
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class controlSprint {

    @Autowired
    repoSprint RS;

    @GetMapping("/Sprint/all")
    public ResponseEntity<List<Sprint>> getAllSprint(){
        try{
            List<Sprint> listaSprint = new ArrayList<Sprint>();
            RS.findAll().forEach(listaSprint::add);
            if(listaSprint.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaSprint,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Sprint/find")
    public ResponseEntity<List<Sprint>> getSprintByID(@RequestParam int id){
        try{
            List <Sprint> foundActivity = RS.findBySprintID(id);
            if(foundActivity.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundActivity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Sprint/create")
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint s){
        try{
            Sprint tdb = RS.save(new Sprint(s.getMetasID(), s.getFecha_Inicio(), s.getFecha_Finalizacion()));
            return new ResponseEntity<>(tdb, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @DeleteMapping("/Sprint/remove")
    public ResponseEntity<HttpStatus> deleteSprint(@RequestParam int id){
        try{
            RS.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/Sprint/update")
    public ResponseEntity<Sprint> updateSprint(@RequestParam int id, @RequestBody Sprint updActividad){
        Optional<Sprint> activitydata = RS.findById(id);

        if(activitydata.isPresent()){
            Sprint s = activitydata.get();
            s.setMetasID(updActividad.getMetasID());
            s.setFecha_Inicio(updActividad.getFecha_Inicio());
            s.setFecha_Finalizacion(updActividad.getFecha_Finalizacion());
            

            return new ResponseEntity<>(RS.save(s), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Sprint/delete/all")
    public ResponseEntity<HttpStatus> deleteAllSprint(){
        try{
            RS.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
