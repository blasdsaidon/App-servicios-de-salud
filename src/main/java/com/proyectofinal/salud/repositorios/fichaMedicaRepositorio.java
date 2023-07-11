/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectofinal.salud.repositorios;


import com.proyectofinal.salud.entidades.fichaMedica;
import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author blasd
 */
@Repository
public interface fichaMedicaRepositorio extends JpaRepository<fichaMedica, String> {
    
    
    
    
    @Query("SELECT fm FROM fichaMedica fm JOIN fm.turno t WHERE t.paciente.idPersona = :idPaciente AND t.medico.idPersona = :idMedico")
    fichaMedica buscarFichaMedicaPorPacienteYMedico(@Param("idPaciente") String idPaciente, @Param("idMedico") String idMedico);
}
