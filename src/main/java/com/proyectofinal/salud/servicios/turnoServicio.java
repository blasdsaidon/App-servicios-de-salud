package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.entidades.turno;
import com.proyectofinal.salud.repositorios.medicoRepositorio;
import com.proyectofinal.salud.repositorios.pacienteRepositorio;
import com.proyectofinal.salud.repositorios.turnoRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class turnoServicio {

    @Autowired
    private pacienteRepositorio pacienteRepo;
    
    @Autowired
    private medicoRepositorio medicoRepo;
    
    @Autowired
    private turnoRepositorio turnoRepo;

    @Autowired
    private medicoServicio medicoServicio;
    
    @Transactional
    public turno crearTurno(medico medico, Date fechaTurno) { //String fechaTurno en lugar de Date ||  throws ParseException
        turno turno = new turno();
        //Date turnoDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(fechaTurno); //Sacandolo por ahora ya que el String que se recibe va a estar en la funcion de crearTrunosDisponibles en medicoServicio

        turno.setMedico(medico);
        turno.setFecha(fechaTurno); //turnoDate en lugar de fecha turno
        turno.setReservado(Boolean.FALSE);
        turnoRepo.save(turno);

        return turno;
    }
    
//---------------------------------------------------------------
    
    @Transactional
    public void crearTurnosDisponibles(String idMedico, String fechaInicioString, 
            String fechaFinString, String horaInicioString, String horaFinString) throws ParseException {

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Optional<medico> respuesta = medicoRepo.findById(idMedico);
        if (respuesta.isPresent()) {
            medico medico = respuesta.get();
            Collection<turno> turnos = medico.getTurnos();

            try {
                Date fechaInicio = formatoFecha.parse(fechaInicioString);
                Date fechaFin = formatoFecha.parse(fechaFinString);
                Date horaInicio = formatoFechaHora.parse(fechaInicioString + " " + horaInicioString);
                Date horaFin = formatoFechaHora.parse(fechaFinString + " " + horaFinString);

                Date fechaActual = fechaInicio;
                while (!fechaActual.after(fechaFin)) {
                    int diaSemana = fechaActual.getDay();
                    if (diaSemana >= 1 && diaSemana <= 5) {  // Lunes a Viernes (1-5)
                        
                        Date fechaHoraTurno = horaInicio;
                        while (!fechaHoraTurno.after(horaFin)) {
                            turno turno = crearTurno(medico, fechaHoraTurno);
                            turnos.add(turno);
                            fechaHoraTurno = aumentarTiempo(fechaHoraTurno, 30);
                        }
                    }
                    horaInicio = aumentarFecha(fechaActual, 1);
                    fechaActual = aumentarFecha(fechaActual, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            medico.setTurnos(turnos);
            medicoRepo.save(medico);
        }
    }

    private Date aumentarFecha(Date fecha, int dias) {
        long millis = fecha.getTime();
        millis += dias * 24 * 60 * 60 * 1000;  // Suma dias en milisegundos
        return new Date(millis);
    }

    private Date aumentarTiempo(Date fechaHora, int minutos) {
        long millis = fechaHora.getTime();
        millis += minutos * 60 * 1000;  // Suma minutos en milisegundos
        return new Date(millis);
    }

//----------------------------------------------------------------
    
    @Transactional
    public void asignarTurno(String idPaciente, turno turno) {

        Optional<paciente> respuesta = pacienteRepo.findById(idPaciente);

        if (respuesta.isPresent()) {
            paciente paciente = respuesta.get();
            turno.setPaciente(paciente);
            turno.setReservado(Boolean.TRUE);
            turnoRepo.save(turno);
            Collection<turno> turnosPaciente = paciente.getTurnos();
            turnosPaciente.add(turno);
            paciente.setTurnos(turnosPaciente);
            pacienteRepo.save(paciente);
        }
    }

    @Transactional
    public void cancelarTurno(String idTurno, String idPaciente) {

        Optional<turno> respuesta = turnoRepo.findById(idTurno);
        Optional<paciente> respuesta2 = pacienteRepo.findById(idPaciente);

        if (respuesta.isPresent() && respuesta2.isPresent()) {
            turno turno = respuesta.get();
            turno.setPaciente(null);
            turno.setReservado(Boolean.FALSE);
            turnoRepo.save(turno);
            paciente paciente = respuesta2.get();
            paciente.getTurnos().remove(turno);
            /*Collection<turno> turnos = paciente.getTurnos();
            Iterator<turno> i = turnos.iterator();
            
            while (i.hasNext()) {
                turno e = i.next();
                if (e.getIdTurno().equals(idTurno)) {
                    i.remove();
                }
            }*/
            pacienteRepo.save(paciente);
        }   
    }

    public Collection<turno> accederTurnosPorMedico(String idMedico) {
        
        medico medico = medicoServicio.buscarMedicoPorID(idMedico);
        
        return medico.getTurnos();
    }
}