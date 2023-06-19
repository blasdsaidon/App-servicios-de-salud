package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuarioRepositorio extends JpaRepository<usuario, String> {

}
