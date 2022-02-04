package com.scrumy.BE.modelos;

import javax.persistence.*;

@Entity
@Table(name="sprint")
public class Sprints {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sprintID;

    @Column(name = "Estado")
    private boolean estado = true;

    @Column(name = "Proyecto")
    private int idProyecto;

    @Column(name = "FechaInicio",nullable = false)
    private String fechaInicio;

    @Column(name = "FechaFinalizacion",nullable = false)
    private String fechaFinalizacion;

    //constructores
    public Sprints() {
    }


    public Sprints( int idProyecto, String fechaInicio, String fechaFinalizacion) {
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

    public boolean getEstado(){
        return this.estado;
    }

    public void setEstado(boolean estado){
        this.estado =  estado;
    }

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
            ", estado='" + getEstado() + "'" +
            ", idProyecto:'" + getidProyecto() + "'" +
            ", fechaInicio:'" + getFechaInicio() + "'" +
            ", fechaFinalizacion:'" + getFechaFinalizacion() + "'" +
            "}";
    }


}
