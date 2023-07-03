package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface medicoRepositorio extends JpaRepository<medico, String> {

    //Buscador de medicos segun especialidad.(Joaquin)
    @Query("SELECT m FROM medico m WHERE m.especialidad = :especialidad")
    public medico buscarPorTitulo(@Param("especialidad") String especialidad);

    @Query("SELECT m FROM medico m WHERE m.email = :email")
    public medico buscarPorEmail(@Param("email") String email);

    @Query("SELECT m FROM medico m WHERE m.telefono = :telefono")
    public medico buscarPorTelefono(@Param("telefono") String telefono);
    

    @Query("SELECT m FROM medico m WHERE m.idPersona = :idPersona")
    public medico buscarMedicoPorID(@Param("idPersona") String idPersona);

    
    /*SELECT turno.fecha, turno.hora, medico.nombre
FROM turnos AS turno
JOIN medicos AS medico ON turno.medico_id = medico.id
JOIN especialidades AS especialidad ON medico.especialidad_id = especialidad.id
WHERE especialidad.nombre = 'nombre_especialidad'*/
}
