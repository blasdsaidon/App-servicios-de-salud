package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.entidades.turno;

import java.util.List;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository


public interface turnoRepositorio extends JpaRepository<turno, String>  {
    
   

    @Query("SELECT DISTINCT t.paciente FROM turno t WHERE t.medico.idPersona = :medicoId")
    List<paciente> obtenerNombresPacientesConTurnoPorMedico(@Param("medicoId") String medicoId);

    // Otras consultas y métodos del repositorio



     @Query("SELECT t FROM turno t WHERE t.medico.idPersona = :idPersona ORDER BY t.fecha DESC")
     public Collection<turno> buscarTurnoPorMedico(@Param("idPersona") String idPersona);


    


    
    

}
