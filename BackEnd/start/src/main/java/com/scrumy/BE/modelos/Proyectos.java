package com.scrumy.BE.modelos;

import javax.persistence.*;

@Entity
@Table (name = "proyecto")
public class Proyectos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int proyectoID;

    @Column(name = "Nombre",nullable = false)
    private String nombre;

    @Column(name = "Usuario",nullable = false)
    private int idUsuarios;

    @Column(name = "Motivacion",nullable = false)
    private String motivacion;

    //Tenemos que volverlo arreglo
    //@Column(name = "Meta")
    //private int idMeta; 

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "FechaFin",nullable = false)
    private String fechaFin;

    //Constructores
    public Proyectos() {
    }

    public Proyectos(String nombre, int idUsuarios, String motivacion,  String descripcion, String fechaFin) {
        this.nombre = nombre;
        this.idUsuarios = idUsuarios;
        this.motivacion = motivacion;
        //this.idMeta = idMeta;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
    }

    public int getProyectoID() {
        return this.proyectoID;
    }

    public void setProyectoID(int proyectoID) {
        this.proyectoID = proyectoID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdUsuarios() {
        return this.idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getMotivacion() {
        return this.motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    /*public int getIdMeta() {
        return this.idMeta;
    }

    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }*/

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "{" +
            " proyectoID='" + getProyectoID() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", idUsuarios='" + getIdUsuarios() + "'" +
            ", motivacion='" + getMotivacion() + "'" +
            //", idUMeta='" + getIdMeta() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }

}

