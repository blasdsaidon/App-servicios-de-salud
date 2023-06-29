
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
    public void crearTurno(String idMedico,String fechaTurno)throws ParseException{
        turno turno = new turno(); 
        Date TurnoDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(fechaTurno); 
        Optional<medico> respuesta = medicoRepo.findById(idMedico);
        if (respuesta.isPresent()) {
           medico medico = respuesta.get();
           turno.setMedico(medico); 
           turno.setFecha(TurnoDate);
           turnoRepo.save(turno);
           Collection<turno> turnosMedicos = medico.getTurnos();
           turnosMedicos.add(turno);
           medico.setTurnos(turnosMedicos);
           medicoRepo.save(medico);
        }
        
    
    }
    
    @Transactional
    public void asignarTurno(String idPaciente,turno turno){
        
        Optional<paciente> respuesta = pacienteRepo.findById(idPaciente);
        
        if (respuesta.isPresent()) {
            paciente paciente = respuesta.get();
            turno.setPaciente(paciente);
            turnoRepo.save(turno);
            Collection<turno> turnosPaciente = paciente.getTurnos();
            turnosPaciente.add(turno);
            paciente.setTurnos(turnosPaciente);
            pacienteRepo.save(paciente);
            
            
        }
    
    
    }
    
    public Collection<turno> accederTurnosPorMedico(String idMedico){
      medico medico =  medicoServicio.buscarMedicoPorID(idMedico);
    return medico.getTurnos();
    }
    
}
