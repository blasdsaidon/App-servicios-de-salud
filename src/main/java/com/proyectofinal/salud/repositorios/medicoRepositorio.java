package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface medicoRepositorio extends JpaRepository<medico, String> {

}
