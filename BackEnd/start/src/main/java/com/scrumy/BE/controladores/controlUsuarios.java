package com.scrumy.BE.controladores;

import java.util.*;

import com.scrumy.BE.modelos.Usuarios;
import com.scrumy.BE.repositorios.repoUsuarios;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;

//removido para poder acceder localmente desde cualquier ip
//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class controlUsuarios {


    @Autowired
    repoUsuarios RU;

    @GetMapping("/usuarios/all")
    public ResponseEntity<List<Usuarios>> getAllUsers(){
        try{
            List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
            RU.findAll().forEach(listaUsuarios::add);
            if(listaUsuarios.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuarios/find")
    public ResponseEntity<Optional<Usuarios>> getUsersbyID(@RequestParam int id){
        try{
            Optional<Usuarios> foundUser = RU.findById(id);
            if(foundUser.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuarios/create")
    public ResponseEntity<Usuarios> createUsuario(@RequestBody Usuarios u){
        try{
            Usuarios tdb = RU.save(new Usuarios(u.getNombre(), u.getCorreo(),u.getContraseña(),u.getIdProyectos()));
            return new ResponseEntity<>(tdb,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/usuarios/remove")
    public ResponseEntity<HttpStatus> deleteUsuario(@RequestParam int id){
        try{
            RU.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/usuarios/update")
    public ResponseEntity<Usuarios> updateUsuario(@RequestParam int id, @RequestBody Usuarios updUser){
        Optional<Usuarios> userdata = RU.findById(id);
            
        if(userdata.isPresent()){
            Usuarios u = userdata.get();
                
            u.setContraseña(updUser.getContraseña());
            u.setCorreo(updUser.getCorreo());
            u.setNombre(updUser.getNombre());
            u.setIdProyectos(updUser.getIdProyectos());
            return new ResponseEntity<>(RU.save(u),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuarios/delete/all")
    public ResponseEntity<HttpStatus> deleteAllUsuarios(){
        try{
            RU.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
}
