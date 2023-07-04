package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface pacienteRepositorio extends JpaRepository<paciente, String> {
    @Query("SELECT p FROM paciente p WHERE p.email = :email")
    public paciente buscarPorEmail(@Param("email") String email);
    @Query("SELECT p FROM paciente p WHERE p.telefono = :telefono")
    public paciente buscarPorTelefono(@Param("telefono") String telefono);
}