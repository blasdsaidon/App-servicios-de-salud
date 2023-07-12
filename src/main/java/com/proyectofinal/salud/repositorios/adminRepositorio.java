package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface adminRepositorio extends JpaRepository<admin, String> {

    @Query("SELECT a FROM admin a WHERE a.email = :email")
    public admin buscarPorEmail(@Param("email") String email);

    @Query("SELECT a FROM admin a WHERE a.telefono = :telefono")
    public admin buscarPorTelefono(@Param("telefono") String telefono);
}