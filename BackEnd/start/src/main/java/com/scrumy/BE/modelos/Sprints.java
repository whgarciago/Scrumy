package com.scrumy.BE.modelos;

import javax.persistence.*;

@Entity
@Table(name="sprint")
public class Sprints {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sprintID;

    //@Column(name = "Meta")
    //private int idMetas;

    @Column(name = "Proyecto")
    private int idProyecto;

    @Column(name = "FechaInicio",nullable = false)
    private String fechaInicio;

    @Column(name = "FechaFinalizacion",nullable = false)
    private String fechaFinalizacion;

    //constructores
    public Sprints() {
    }


    public Sprints(/*int idMetas,*/ int idProyecto, String fechaInicio, String fechaFinalizacion) {
        //this.idMetas = idMetas;
        this.idProyecto = idProyecto;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
    }
    

    public int getSprintID() {
        return this.sprintID;
    }

    public void setSprintID(int sprintID) {
        this.sprintID = sprintID;
    }

    /*public int getIdMetas() {
        return this.idMetas;
    }

    public void setIdMetas(int idMetas) {
        this.idMetas = idMetas;
    }*/

    public int getidProyecto() {
        return this.idProyecto;
    }

    public void setidProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinalizacion() {
        return this.fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    @Override
    public String toString() {
        return "{" +
            " sprintID:'" + getSprintID() + "'" +
            //", idMetas='" + getIdMetas() + "'" +
            ", idProyecto:'" + getidProyecto() + "'" +
            ", fechaInicio:'" + getFechaInicio() + "'" +
            ", fechaFinalizacion:'" + getFechaFinalizacion() + "'" +
            "}";
    }


}
