package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.enumeradores.especialidad;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface medicoRepositorio extends JpaRepository<medico, String> {

    @Query("SELECT m FROM medico m WHERE m.especialidad = :especialidad")
    public medico buscarPorEspecialidad(@Param("especialidad") String especialidad);

    @Query("SELECT m FROM medico m WHERE m.especialidad = :especialidad")
    public List<medico> buscarNombresPorEspecialidad(@Param("especialidad") especialidad especialidad);

    @Query("SELECT m FROM medico m WHERE m.nombre = :nombre")
    public List<medico> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT m FROM medico m WHERE m.email = :email")
    public medico buscarPorEmail(@Param("email") String email);

    @Query("SELECT m FROM medico m WHERE m.telefono = :telefono")
    public medico buscarPorTelefono(@Param("telefono") String telefono);

    @Query("SELECT m FROM medico m WHERE m.idPersona = :idPersona")
    public medico buscarMedicoPorID(@Param("idPersona") String idPersona);

    @Query("SELECT m FROM medico m WHERE m.especialidad = :especialidad")
    public Collection<medico> listarMedicosPorEspecialidad(@Param("especialidad") String especialidad);
}