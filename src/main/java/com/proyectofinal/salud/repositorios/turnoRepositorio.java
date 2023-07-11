package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.entidades.turno;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.Collection;
>>>>>>> 27d415d1fc31790978d89467e53c92d56099a46e
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface turnoRepositorio extends JpaRepository<turno, String>  {
    
   

    @Query("SELECT DISTINCT t.paciente FROM turno t WHERE t.medico.idPersona = :medicoId")
    List<paciente> obtenerNombresPacientesConTurnoPorMedico(@Param("medicoId") String medicoId);

    // Otras consultas y métodos del repositorio
=======
public interface turnoRepositorio extends JpaRepository<turno, String> {

     @Query("SELECT t FROM turno t WHERE t.medico.idPersona = :idPersona ORDER BY t.fecha DESC")
     public Collection<turno> buscarTurnoPorMedico(@Param("idPersona") String idPersona);
>>>>>>> 27d415d1fc31790978d89467e53c92d56099a46e
}
    

