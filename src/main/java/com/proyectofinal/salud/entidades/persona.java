package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.rol;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
//Solucion Atributos clase abstracta para que sean heredados a las hijas
@MappedSuperclass
public abstract class persona {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idPersona;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String email;
    private String telefono;
    @OneToOne
    private imagen imagen;
    private String password;
    @Enumerated(EnumType.STRING)
    private rol rol;

    public persona() {
    }

    public persona(String idPersona, String nombre, String apellido, String email, String telefono, imagen imagen, String password, rol rol) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.imagen = imagen;
        this.password = password;
        this.rol = rol;
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

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public imagen getImagen() {
        return imagen;
    }

    public void setImagen(imagen imagen) {
        this.imagen = imagen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public rol getRol() {
        return rol;
    }

    public void setRol(rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Persona: " + "{Nombre = " + nombre + "}\n"
                +"{Apellido = " + apellido + "}\n"
                +"{Email = " + email + "}\n"
                +"{Telefono = " + telefono + '}';
    }

 
}
