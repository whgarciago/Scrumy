package com.scrumy.BE.modelos;

import javax.persistence.*;

@Entity
@Table(name = "actividades")
public class Actividades {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int actividadID;

    @Column(name = "Dificultad")
    private String dificultad;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "IDMeta")
    private int idMeta;

    //Constructores

    public Actividades() {
    }

    public Actividades(String dificultad, String nombre, String descripcion, int idMeta) {
        this.dificultad = dificultad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idMeta = idMeta;
    }
    
    //Getters y Setters
    public int getActividadID() {
        return this.actividadID;
    }

    public void setActividadID(int actividadID) {
        this.actividadID = actividadID;
    }

    public String getDificultad() {
        return this.dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdMeta() {
        return this.idMeta;
    }

    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }

    //metodo ToString
    @Override
    public String toString() {
        return "{" +
            " actividadID='" + getActividadID() + "'" +
            ", dificultad='" + getDificultad() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", idMeta='" + getIdMeta() + "'" +
            "}";
    }

    
}