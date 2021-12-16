package com.scrumy.BE.controladores;


import java.util.*;

import com.scrumy.BE.modelos.Proyectos;
import com.scrumy.BE.repositorios.repoProyectos;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.*;

//removido para poder acceder localmente desde cualquier ip
//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class controlProyecto {
    
    @Autowired
    repoProyectos RP;


    @GetMapping("/proyectos/all")
    public ResponseEntity<List<Proyectos>> getAllProjectsByidUsuarEntity(@RequestParam int id){

        try{
            List<Proyectos> listaProyectos = new ArrayList<Proyectos>();
            RP.findByidUsuarios(id).forEach(listaProyectos::add);
            if(listaProyectos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaProyectos, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/proyectos/find")
    public ResponseEntity<Optional<Proyectos>> getProjectsbyID(@RequestParam int id){
        try{
            Optional<Proyectos> foundProject = RP.findById(id);
            if(foundProject.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundProject, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/proyectos/create")
    public ResponseEntity<Proyectos> createProyecto(@RequestBody Proyectos u){
        try{
            Proyectos tdb = RP.save(new Proyectos(u.getNombre(), u.getIdUsuarios(),u.getMotivacion(),u.getDescripcion(),u.getFechaFin()));
            return new ResponseEntity<>(tdb,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/proyectos/remove")
    public ResponseEntity<HttpStatus> deleteProyecto(@RequestParam int id){
        try{
            RP.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/proyectos/update")
    public ResponseEntity<Proyectos> updateProyecto(@RequestParam int id, @RequestBody Proyectos updProyect){
        Optional<Proyectos> proyectdata = RP.findById(id);
            
        if(proyectdata.isPresent()){
            Proyectos p = proyectdata.get();            
            p.setDescripcion(updProyect.getDescripcion());
            p.setFechaFin(updProyect.getFechaFin());
            //p.setIdMeta(updProyect.getIdMeta());
            p.setIdUsuarios(updProyect.getIdUsuarios());
            p.setMotivacion(updProyect.getMotivacion());
            p.setNombre(updProyect.getNombre());

            return new ResponseEntity<>(RP.save(p),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/proyectos/delete/all")
    public ResponseEntity<HttpStatus> deleteAllProyectos(){
        try{
            RP.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

