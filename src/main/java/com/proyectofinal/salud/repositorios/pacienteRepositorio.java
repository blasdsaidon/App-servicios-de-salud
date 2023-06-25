package com.proyectofinal.salud.repositorios;

import com.proyectofinal.salud.entidades.paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface pacienteRepositorio extends JpaRepository<paciente, String> {
   //Buscador de pacientes segun el genero.(Joaquin)
//    @Query("SELECT p FROM paciente p WHERE p.sexo = :sexo")
//    public paciente buscarPacientesPorSexo(@Param("sexo")String sexo);
//     @Query("SELECT p FROM paciente p WHERE p.apellido = :apellido")
//    public paciente buscarPacientesPorApellido(@Param("apellido")String apellido);
    @Query("SELECT p FROM paciente p WHERE p.email = :email")
    public paciente buscarPorEmail(@Param("email") String email);
    @Query("SELECT p FROM paciente p WHERE p.telefono = :telefono")
    public paciente buscarPorTelefono(@Param("telefono") String telefono);
}