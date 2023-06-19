package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface imagenRepositorio extends JpaRepository<imagen, String> {

}
