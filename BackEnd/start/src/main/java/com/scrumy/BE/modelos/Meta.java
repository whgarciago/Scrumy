package com.scrumy.BE.modelos;

import javax.persistence.*;


@Entity
@Table(name = "meta")
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int metaID;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Proyecto")
    private int idProyecto;

    @Column(name = "Estado")
    private int estado;

    //Tenemos que volverlo arreglo
    @Column(name = "Sprint")
    private int idSprint; 

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Actividad")
    private int actividadID;

    //Constructores
    public Meta() {
    }

    public Meta(String nombre, int idProyecto, int estado, int idSprint, String descripcion, int actividadID) {
        this.nombre = nombre;
        this.idProyecto = idProyecto;
        this.estado = estado;
        this.idSprint = idSprint;
        this.descripcion = descripcion;
        this.actividadID = actividadID;
    }


    public Meta(int metaID, String nombre, int idProyecto, int estado, String descripcion) {
        this.metaID = metaID;
        this.nombre = nombre;
        this.idProyecto = idProyecto;
        this.estado = estado;
        this.descripcion = descripcion;
    }
    
    public int getMetaID() {
        return this.metaID;
    }

    public void setMetaID(int metaID) {
        this.metaID = metaID;
    }

    public int getIdProyecto() {
        return this.idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdSprint() {
        return this.idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public int getActividadID() {
        return this.actividadID;
    }

    public void setActividadID(int actividadID) {
        this.actividadID = actividadID;
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


    @Override
    public String toString() {
        return "{" +
            " metaID='" + getMetaID() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", idProyecto='" + getIdProyecto() + "'" +
            ", estado='" + getEstado() + "'" +
            ", idSprint='" + getIdSprint() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", actividadID='" + getActividadID() + "'" +
            "}";
    }
    
}
