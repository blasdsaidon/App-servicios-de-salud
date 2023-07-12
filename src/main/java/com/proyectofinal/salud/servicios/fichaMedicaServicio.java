/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.fichaMedica;
import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.entidades.turno;
import com.proyectofinal.salud.repositorios.fichaMedicaRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author blasd
 */
@Service
public class fichaMedicaServicio {
    
    @Autowired
    private fichaMedicaRepositorio fichaMedicaRepo;
    
    public fichaMedica traerFichaMedica(paciente paciente, medico medico) {
        // Obtener la ficha médica correspondiente al turno actual
        fichaMedica fichaMedica = fichaMedicaRepo.buscarFichaMedicaPorPacienteYMedico(paciente.getIdPersona(), medico.getIdPersona());
        
        return fichaMedica;
        
        }
    
    
    public void crearFicha(turno turno){
        fichaMedica ficha = new fichaMedica();
        ficha.setTurno(turno);
        fichaMedicaRepo.save(ficha);
        
    }
    
    public void agregarNota(String idFicha, String nota){
        Optional<fichaMedica> respuesta = fichaMedicaRepo.findById(idFicha);
         fichaMedica ficha = respuesta.get();
         ficha.setNotas(nota);
         fichaMedicaRepo.save(ficha);
    }
    
            
    }
    

