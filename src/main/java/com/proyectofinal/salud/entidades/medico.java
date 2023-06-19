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
    @OneToMany
    private Collection<fichaMedica> historialConsultas;
    @OneToMany
    private Collection<turno> turnos;
    private Boolean alta;

    public medico() {
    }


    public medico(String idMedico, especialidad especialidad, obraSocial obraSocialRecibida, ArrayList<Date> horariosDisponibles, Integer valorConsultar, Integer calificacionServicio, Collection<fichaMedica> historialConsultas, Collection<turno> turnos, Boolean alta, String nombre, String apellido, String email, String telefono) {

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
