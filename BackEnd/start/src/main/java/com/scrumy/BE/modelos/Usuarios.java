package com.scrumy.BE.modelos;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int usuarioID;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Contraseña")
    private String contraseña;

    @Column(name = "IDProyectos")
    private int idProyectos;

    //Constructores
    public Usuarios() {
    }


    public Usuarios (String Nombre, String Correo, String Contraseña, int IDProyectos){
        this.nombre = Nombre;
        this.correo = Correo;
        this.contraseña = Contraseña;
        this.idProyectos = IDProyectos;
    }

    //Getters y setters

    public int getUsuarioID() {
        return this.usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getIdProyectos() {
        return this.idProyectos;
    }

    public void setIdProyectos(int idProyectos) {
        this.idProyectos = idProyectos;
    }
    

    //ToString temporal de visualizacion
    @Override
    public String toString() {
        return "{" +
            " usuarioID='" + getUsuarioID() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", contraseña='" + getContraseña() + "'" +
            ", idProyectos='" + getIdProyectos() + "'" +
            "}";
    }

    
    
}
