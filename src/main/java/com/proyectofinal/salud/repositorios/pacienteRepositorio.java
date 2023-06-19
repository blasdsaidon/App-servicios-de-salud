package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pacienteRepositorio extends JpaRepository<paciente, String> {

}