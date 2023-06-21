package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.sexo;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class paciente extends persona {
   
    @Enumerated(EnumType.STRING)
    private obraSocial obraSocial;
    @OneToMany
    private Collection<fichaMedica> historialMedico;
//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    private Date fechaNacimiento;
    private String fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private sexo genero;
    @OneToMany
    private Collection<turno> turnos;
    
    public paciente() {
    }

    public paciente( obraSocial obraSocial, Collection<fichaMedica> historialMedico, String fechaNacimiento, sexo genero, Collection<turno> turnos, String idPersona, String nombre, String apellido, String email, String telefono, com.proyectofinal.salud.entidades.imagen imagen, String password, com.proyectofinal.salud.enumeradores.rol rol) {
        super(idPersona, nombre, apellido, email, telefono, imagen, password, rol);
        this.obraSocial = obraSocial;
        this.historialMedico = historialMedico;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.turnos = turnos;
    }  

    public obraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(obraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Collection<fichaMedica> getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(Collection<fichaMedica> historialMedico) {
        this.historialMedico = historialMedico;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public sexo getGenero() {
        return genero;
    }

    public void setGenero(sexo genero) {
        this.genero = genero;
    }

    public Collection<turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Collection<turno> turnos) {
        this.turnos = turnos;
    }

    @Override
    public String toString() {
        return "Paciente: " + "{Id del Paciente = " + this.getIdPersona() + "}\n"
                +"{Obra Social = " + obraSocial + "}\n"
                +"{Historial Medico = " + historialMedico + "}\n"
                +"{Fecha De Nacimiento = " + fechaNacimiento + "}\n"
                +"{Sexo = " + genero + "}\n"
                +"{Turnos = " + turnos + '}';
    }
}
