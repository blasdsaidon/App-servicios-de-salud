package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.obraSocial;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class paciente extends persona {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idPaciente;
    @Enumerated(EnumType.STRING)
    private obraSocial obraSocial;
    @OneToMany
    private Collection<fichaMedica> historialMedico;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    private String sexo;
    @OneToMany
    private Collection<turno> turnos;

    public paciente() {
    }

    public paciente(String idPaciente, obraSocial obraSocial, Collection<fichaMedica> historialMedico, Date fechaNacimiento, String sexo, Collection<turno> turnos, String nombre, String apellido, String email, String telefono) {
        super(nombre, apellido, email, telefono);
        this.idPaciente = idPaciente;
        this.obraSocial = obraSocial;
        this.historialMedico = historialMedico;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.turnos = turnos;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Collection<turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Collection<turno> turnos) {
        this.turnos = turnos;
    }

    @Override
    public String toString() {
        return "Paciente: " + "{Id del Paciente = " + idPaciente + "} {Obra Social = " + obraSocial + "} {Historial Medico = " + historialMedico + "} {Fecha De Nacimiento = " + fechaNacimiento + "} {Sexo = " + sexo + "} {Turnos = " + turnos + '}';
    }
}
