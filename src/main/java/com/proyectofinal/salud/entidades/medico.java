package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import java.util.Date;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class medico extends persona {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idMedico;
    @Enumerated(EnumType.STRING)
    private especialidad especialidad;
    @Enumerated(EnumType.STRING)
    private obraSocial obraSocialRecibida;
    private ArrayList<Date> horariosDisponibles;
    private Integer valorConsulta;
    private Integer calificacionServicio;
    private ArrayList<fichaMedica> historialConsultas;
    private ArrayList<turno> turnos;
    private Boolean alta;

    public medico() {
    }

    public medico(String idMedico, especialidad especialidad, obraSocial obraSocialRecibida, ArrayList<Date> horariosDisponibles, Integer valorConsulta, Integer calificacionServicio, ArrayList<fichaMedica> historialConsultas, ArrayList<turno> turnos, Boolean alta, String nombre, String apellido, String email, String telefono) {
        super(nombre, apellido, email, telefono);
        this.idMedico = idMedico;
        this.especialidad = especialidad;
        this.obraSocialRecibida = obraSocialRecibida;
        this.horariosDisponibles = horariosDisponibles;
        this.valorConsulta = valorConsulta;
        this.calificacionServicio = calificacionServicio;
        this.historialConsultas = historialConsultas;
        this.turnos = turnos;
        this.alta = alta;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public obraSocial getObraSocialRecibida() {
        return obraSocialRecibida;
    }

    public void setObraSocialRecibida(obraSocial obraSocialRecibida) {
        this.obraSocialRecibida = obraSocialRecibida;
    }

    public ArrayList<Date> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public void setHorariosDisponibles(ArrayList<Date> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }

    public Integer getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Integer valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public Integer getCalificacionServicio() {
        return calificacionServicio;
    }

    public void setCalificacionServicio(Integer calificacionServicio) {
        this.calificacionServicio = calificacionServicio;
    }

    public ArrayList<fichaMedica> getHistorialConsultas() {
        return historialConsultas;
    }

    public void setHistorialConsultas(ArrayList<fichaMedica> historialConsultas) {
        this.historialConsultas = historialConsultas;
    }

    public ArrayList<turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<turno> turnos) {
        this.turnos = turnos;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    @Override
    public String toString() {
        return "Medico: {" + "Id Medico = " + idMedico + "}\n"
                +"{Especialidad = " + especialidad + "}\n"
                +"{Obra Social Recibida = " + obraSocialRecibida + "}\n"
                +"{Horarios Disponibles = " + horariosDisponibles + "}\n"
                +"{Valor De La Consulta = " + valorConsulta + "}\n"
                +"{Calificación Del Servicio = " + calificacionServicio + "}\n"
                +"{Historial De Consultas = " + historialConsultas + "}\n"
                +"{Turnos = " + turnos + "}\n"
                +"{Alta = " + alta + '}';
    }
}
