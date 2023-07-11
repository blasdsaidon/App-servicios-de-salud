/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.fichaMedica;
import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.repositorios.fichaMedicaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }
    

