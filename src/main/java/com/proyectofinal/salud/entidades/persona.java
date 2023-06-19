package com.proyectofinal.salud.entidades;

import javax.persistence.Column;

public abstract class persona {

    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String email;
    private String telefono;

    public persona() {
    }

    public persona(String nombre, String apellido, String email, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona: " + "{Nombre = " + nombre + "} {Apellido = " + apellido + "} {Email = " + email + "} {Telefono = " + telefono + '}';
    }
}
