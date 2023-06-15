package com.proyectofinal.salud.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idTurno;
    @OneToOne
    private paciente paciente;
    @OneToOne
    private medico medico;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public turno() {
    }

    public turno(String idTurno, paciente paciente, medico medico, Date fecha) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(paciente paciente) {
        this.paciente = paciente;
    }

    public medico getMedico() {
        return medico;
    }

    public void setMedico(medico medico) {
        this.medico = medico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "turno{" + "idTurno=" + idTurno + ", paciente=" + paciente + ", medico=" + medico + ", fecha=" + fecha + '}';
    }
}