package com.proyectofinal.salud.entidades;

import com.proyectofinal.salud.enumeradores.rol;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idUsuario;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private rol rol;
    @OneToOne
    private imagen imagen;

    public usuario(String idUsuario, String email, String password, rol rol, imagen imagen) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.imagen = imagen;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public imagen getImagen() {
        return imagen;
    }

    public void setImagen(imagen imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Usuario: {" + "Id Usuario = " + idUsuario + "}\n"
                +"{Email = " + email + "}\n"
                +"{Password = " + password + "}\n"
                +"{Rol = " + rol + "}\n"
                +"{Imagen = " + imagen + '}';
    }
}
