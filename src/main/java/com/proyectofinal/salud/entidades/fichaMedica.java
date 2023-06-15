package com.proyectofinal.salud.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class fichaMedica {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idFichaMedica;
    private String notas;
    @OneToOne
    private turno turno;

    public fichaMedica() {
    }
    
       public fichaMedica(String idFichaMedica, String notas, turno turno) {
        this.idFichaMedica = idFichaMedica;
        this.notas = notas;
        this.turno = turno;
    }
       
    public String getIdFichaMedica() {
        return idFichaMedica;
    }

    public void setIdFichaMedica(String idFichaMedica) {
        this.idFichaMedica = idFichaMedica;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public turno getTurno() {
        return turno;
    }

    public void setTurno(turno turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "fichaMedica{" + "idFichaMedica=" + idFichaMedica + ", notas=" + notas + ", turno=" + turno + '}';
    }
     
}
 
