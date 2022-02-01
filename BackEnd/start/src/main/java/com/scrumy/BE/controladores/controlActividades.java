package com.scrumy.BE.controladores;

import java.util.*;

import com.scrumy.BE.modelos.Actividades;
import com.scrumy.BE.modelos.Meta;
import com.scrumy.BE.repositorios.repoActividades;
import com.scrumy.BE.repositorios.repoMeta;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

//Inhabilitado por pruebas, habilitar para permitir conexiones IP
//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class controlActividades {

    @Autowired
    repoActividades RA;

    @Autowired
    repoMeta RM;

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

    @GetMapping("/actividades/findMeta")
    public ResponseEntity<List<Actividades>> getActivitiesByMetaID(@RequestParam int id){
        try{
            List <Actividades> foundActivity = RA.findByidMeta(id);
            if(foundActivity.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundActivity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/metas/avanceMeta")
    public Double avanceByMetaID(@RequestParam int id){
        try{
            List <Actividades> foundActivity = RA.findByidMeta(id);
            if(foundActivity.isEmpty()){
                return 0.0;
            }
            double actCompletas = 0.0;
            for(int i = 0; i<foundActivity.size();i++){
                if(foundActivity.get(i).getEstado() == true){
                    actCompletas = actCompletas + 1.0;
                }else{
                    continue;
                }
            }
            double d = Double.valueOf(foundActivity.size());
            return (actCompletas/d)*100.0;
        }catch(Exception e){
            return -1.0;
        }
    }

    @GetMapping("/sprints/avance")
    public Double avanceSprint(@RequestParam int id){
        List<Meta> metas = new ArrayList<Meta>();
        RM.findByidSprint(id).forEach(metas::add);
        if(metas.isEmpty()){
            return 0.0;
        }
        //Double d = 0.0;

        List<Actividades> Activ = new ArrayList<Actividades>();
        for(int i = 0;i<metas.size();i++){
            int index = metas.get(i).getMetaID();
            RA.findByidMeta(index).forEach(Activ::add);
        }

        double completa = 0.0;
        for(int j = 0; j < Activ.size();j++){
            if(Activ.get(j).getEstado()){
                completa = completa + 1.0;
            }else{
                continue;
            }
        }

        return (completa/Double.valueOf(Activ.size()))*100.0;
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
            Actividades tdb = RA.save(new Actividades(a.getDificultad(),a.getNombre(),a.getDescripcion(),a.getIdMeta(),a.getEstado()));
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
            a.setDificultad(updActividad.getDificultad());
            a.setEstado(updActividad.getEstado());


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

        if(activitydata.isPresent()){
            Actividades a = activitydata.get();
            boolean s = a.getEstado();
            a.setEstado(!s);
            return new ResponseEntity<>(RA.save(a), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
