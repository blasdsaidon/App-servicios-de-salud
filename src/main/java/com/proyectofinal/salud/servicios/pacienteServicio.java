package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.repositorios.pacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class pacienteServicio {
    
    @Autowired
    private pacienteRepositorio pacienteRepo;
    
    public void crearPaciente(){
    
    }
}
