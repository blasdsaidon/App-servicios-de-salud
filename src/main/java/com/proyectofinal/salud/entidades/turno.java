package com.proyectofinal.salud.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class turno {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idTurno;
    @ManyToOne
    private paciente paciente;
    @ManyToOne
    private medico medico;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private Boolean reservado;

    public turno() {
    }

    public turno(String idTurno, paciente paciente, medico medico, Date fecha, Boolean reservado) {
        this.idTurno = idTurno;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.reservado = false;
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

    public Boolean getReservado() {
        return reservado;
    }

    public void setReservado(Boolean reservado) {
        this.reservado = reservado;
    }

    @Override
    public String toString() {
        return "Turno: {" + "Id Turno = " + idTurno + "}\n"
                + "{Paciente = " + paciente + "}\n"
                + "{Medico = " + medico + "}\n"
                + "{Fecha = " + fecha + "} \""
                + "{Reservado = " + reservado + '}';
    }
}