/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.turno;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface turnoRepositorio extends JpaRepository<turno, String>  {
    
    @Query("SELECT t FROM turno t WHERE t.medico.idPersona = :idPersona AND t.reservado = : reservado")
    public Collection<turno> buscarTurnoPorMedico(@Param("idPersona") String idPersona,@Param("reservado") Boolean reservado);
    
}
