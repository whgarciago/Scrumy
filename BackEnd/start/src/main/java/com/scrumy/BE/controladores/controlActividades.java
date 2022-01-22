package com.scrumy.BE.controladores;

import java.util.*;

import com.scrumy.BE.modelos.Actividades;
import com.scrumy.BE.repositorios.repoActividades;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

//Inhabilitado por pruebas, habilitar para permitir conexiones IP
@CrossOrigin(origins = "http://localhost:8081")
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class controlActividades {

    @Autowired
    repoActividades RA;

    @GetMapping("/actividades/all")
    public ResponseEntity<List<Actividades>> getAllActivitiesByMeta(@RequestParam int id){
        try{
            List<Actividades> listaActividades = new ArrayList<Actividades>();
            RA.findByidMeta(id).forEach(listaActividades::add);
            if(listaActividades.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaActividades,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/actividades/find")
    public ResponseEntity<Optional<Actividades>> getActivitiesByID(@RequestParam int id){
        try{
            Optional <Actividades> foundActivity = RA.findByActividadID(id);
            if(foundActivity.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundActivity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/actividades/findDificulty")
    public ResponseEntity<String> getDificultyByActivity(@RequestParam int id){
        try{
            Optional <Actividades> foundActivity = RA.findByActividadID(id);
            if(foundActivity.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Actividades a = foundActivity.get();
            return new ResponseEntity<>(a.getDificultad(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/actividades/findState")
    public ResponseEntity<Boolean> getStateByActivity(@RequestParam int id){
        try{
            Optional <Actividades> foundActivity = RA.findByActividadID(id);
            if(foundActivity.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Actividades a = foundActivity.get();
            return new ResponseEntity<>(a.getEstado(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actividades/create")
    public ResponseEntity<Actividades> createActividad(@RequestBody Actividades a){
        try{
            Actividades tdb = RA.save(new Actividades(a.getNombre(), a.getDescripcion(), a.getIdMeta()));
            return new ResponseEntity<>(tdb, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @DeleteMapping("/actividades/remove")
    public ResponseEntity<HttpStatus> deleteActivity(@RequestParam int id){
        try{
            RA.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/actividades/update")
    public ResponseEntity<Actividades> updateActividad(@RequestParam int id, @RequestBody Actividades updActividad){
        Optional<Actividades> activitydata = RA.findById(id);

        if(activitydata.isPresent()){
            Actividades a = activitydata.get();
            a.setDescripcion(updActividad.getDescripcion());
            a.setIdMeta(updActividad.getIdMeta());
            a.setNombre(updActividad.getNombre());
            

            return new ResponseEntity<>(RA.save(a), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/actividades/updateDificulties")
    public ResponseEntity<Actividades> updateDificultyInActividad(@RequestParam int id, @RequestBody Actividades updActividad){
        Optional<Actividades> activitydata = RA.findById(id);

        if(activitydata.isPresent()){
            Actividades a = activitydata.get();
            a.setDificultad(updActividad.getDificultad());
            
            return new ResponseEntity<>(RA.save(a), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/actividades/updateState")
    public ResponseEntity<Actividades> updateState(@RequestParam int id){
        Optional<Actividades> activitydata = RA.findById(id);

        try{
            Actividades a = activitydata.get();
            a.setEstado(!a.getEstado());
            return new ResponseEntity<>(RA.save(a), HttpStatus.OK);

        }catch(Exception e){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/actividades/delete/all")
    public ResponseEntity<HttpStatus> deleteAllActividades(){
        try{
            RA.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
