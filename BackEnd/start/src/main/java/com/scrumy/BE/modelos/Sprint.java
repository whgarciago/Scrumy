package com.scrumy.BE.modelos;

import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name = "sprint")

public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID_sprint;

    @Column(name = "ID_Metas")
    private int MetasID;
    
    @Column(name = "Fecha_Inicio")
    private Date Fecha_Inicio;
    
    @Column(name = "Fecha_Finalizacion")
    private Date Fecha_Finalizacion;

    public Sprint() {

    }

    public Sprint(int MetasID, Date Fecha_Inicio, Date Fecha_Finalizacion) {
        this.MetasID = MetasID;
        this.Fecha_Inicio = Fecha_Inicio;
        this.Fecha_Finalizacion = Fecha_Finalizacion;
    }

    public int getMetasID() {
        return MetasID;
    }

    public void setMetasID(int metasID) {
        MetasID = metasID;
    }

    public int getID_sprint() {
        return ID_sprint;
    }

    public Date getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public Date getFecha_Finalizacion() {
        return Fecha_Finalizacion;
    }

    public void setFecha_Inicio(Date Fecha_Inicio){
        this.Fecha_Inicio = Fecha_Inicio;
    }

    public void setFecha_Finalizacion(Date Fecha_Finalizacion){
        this.Fecha_Finalizacion = Fecha_Finalizacion;
    }
    
    @Override
    public String toString() {
        return "{" +
            " Meta ID ='" + getMetasID() + "'" +
            ", Sprint ID='" + getID_sprint() + "'" +
            ", Fecha inicio ='" + getFecha_Inicio() + "'" +
            ", Fecha finalizacion ='" + getFecha_Finalizacion() + "'" +
            "}";
    }
}