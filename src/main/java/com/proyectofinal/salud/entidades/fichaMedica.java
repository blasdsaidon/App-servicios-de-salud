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
    private String idfichaMedica;
    private String notas;
    @OneToOne
    private turno turno;

    public fichaMedica() {
    }

    public fichaMedica(String idfichaMedica, String notas, turno turno) {
        this.idfichaMedica = idfichaMedica;
        this.notas = notas;
        this.turno = turno;
    }
    
    public String getIdfichaMedica() {
        return idfichaMedica;
    }

    public void setIdfichaMedica(String idfichaMedica) {
        this.idfichaMedica = idfichaMedica;
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
        return "Ficha Medica: {" + "Notas = " + notas + "}\n"
                +"{Turno = " + turno + '}';
    }
}