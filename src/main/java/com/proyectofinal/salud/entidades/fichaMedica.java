package com.proyectofinal.salud.entidades;

public class fichaMedica {

    private String notas;
    private turno turno;

    public fichaMedica() {
    }

    public fichaMedica(String notas, turno turno) {
        this.notas = notas;
        this.turno = turno;
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
        return "fichaMedica{" + "notas=" + notas + ", turno=" + turno + '}';
    }
}
