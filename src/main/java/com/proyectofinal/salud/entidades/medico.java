package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import java.util.Collection;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
public class medico extends persona {

    @Enumerated(EnumType.STRING)
    private especialidad especialidad;
    @ElementCollection(targetClass = obraSocial.class)
    @Enumerated(EnumType.STRING)
    
    private Collection<obraSocial> obraSocialRecibida;
    @OneToMany

    private Collection<turno> horariosDisponibles;
    private Integer valorConsulta;
    private Integer calificacionServicio;
    @OneToMany
    private Collection<fichaMedica> historialConsultas;
    @OneToMany(mappedBy="medico")
    private Collection<turno> turnos;
    private Boolean alta;
    
    public medico() {
    }

    public medico( especialidad especialidad, Collection<obraSocial> obraSocialRecibida, Collection<turno> horariosDisponibles, Integer valorConsulta, Integer calificacionServicio, Collection<fichaMedica> historialConsultas, Collection<turno> turnos, Boolean alta, String idPersona, String nombre, String apellido, String email, String telefono, com.proyectofinal.salud.entidades.imagen imagen, String password, com.proyectofinal.salud.enumeradores.rol rol) {
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

    public Collection<obraSocial> getObraSocialRecibida() {
        return obraSocialRecibida;
    }

    public void setObraSocialRecibida(Collection<obraSocial> obraSocialRecibida) {
        this.obraSocialRecibida = obraSocialRecibida;
    }

    public Collection<turno> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public void setHorariosDisponibles(Collection<turno> horariosDisponibles) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("medico{");
        sb.append("especialidad=").append(especialidad);
        sb.append(", obraSocialRecibida=").append(obraSocialRecibida);
        sb.append(", horariosDisponibles=").append(horariosDisponibles);
        sb.append(", valorConsulta=").append(valorConsulta);
        sb.append(", calificacionServicio=").append(calificacionServicio);
        sb.append(", historialConsultas=").append(historialConsultas);
        sb.append(", turnos=").append(turnos);
        sb.append(", alta=").append(alta);
        sb.append('}');
        return sb.toString();
    }

    
}
