package com.proyectofinal.salud.entidades;

import javax.persistence.Entity;

@Entity
public class admin extends persona {

    public admin() {
    }
    

    public admin(String idPersona, String nombre, String apellido, String email, String telefono, com.proyectofinal.salud.entidades.imagen imagen, String password, com.proyectofinal.salud.enumeradores.rol rol) {
        super(idPersona, nombre, apellido, email, telefono, imagen, password, rol);
    } 
}
