package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class medico extends persona {

    
    @Enumerated(EnumType.STRING)
    private especialidad especialidad;
    @Enumerated(EnumType.STRING)
    private obraSocial obraSocialRecibida;
    private ArrayList<Date> horariosDisponibles;
    private Integer valorConsulta;
    private Integer calificacionServicio;
    @OneToMany
    private Collection<fichaMedica> historialConsultas;
    @OneToMany
    private Collection<turno> turnos;
    private Boolean alta;
    public medico() {
    }

    public medico( especialidad especialidad, obraSocial obraSocialRecibida, ArrayList<Date> horariosDisponibles, Integer valorConsulta, Integer calificacionServicio, Collection<fichaMedica> historialConsultas, Collection<turno> turnos, Boolean alta, String idPersona, String nombre, String apellido, String email, String telefono, com.proyectofinal.salud.entidades.imagen imagen, String password, com.proyectofinal.salud.enumeradores.rol rol) {
        super(idPersona, nombre, apellido, email, telefono, imagen, password, rol);
        this.especialidad = especialidad;
        this.obraSocialRecibida = obraSocialRecibida;
        this.horariosDisponibles = horariosDisponibles;
        this.valorConsulta = valorConsulta;
        this.calificacionServicio = calificacionServicio;
        this.historialConsultas = historialConsultas;
        this.turnos = turnos;
        this.alta = alta;
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

    public Collection<fichaMedica> getHistorialConsultas() {
        return historialConsultas;
    }

    public void setHistorialConsultas(Collection<fichaMedica> historialConsultas) {
        this.historialConsultas = historialConsultas;
    }

    public Collection<turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Collection<turno> turnos) {
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
        return "medico{" + "especialidad=" + especialidad + ", obraSocialRecibida=" + obraSocialRecibida + ", horariosDisponibles=" + horariosDisponibles + ", valorConsulta=" + valorConsulta + ", calificacionServicio=" + calificacionServicio + ", historialConsultas=" + historialConsultas + ", turnos=" + turnos + ", alta=" + alta + '}';
    }

   
}
