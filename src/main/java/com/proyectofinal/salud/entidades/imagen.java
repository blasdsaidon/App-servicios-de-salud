package com.proyectofinal.salud.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idImagen;
    private String mime;
    private String nombre;
    @Lob@Basic(fetch = FetchType.LAZY)
    private byte[] archivo;

    public imagen() {
    }

    public imagen(String idImagen, String mime, String nombre, byte[] archivo) {
        this.idImagen = idImagen;
        this.mime = mime;
        this.nombre = nombre;
        this.archivo = archivo;
    }
   
    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Imagen: {" + "Id Imagen = " + idImagen + "}\n"
                +"{Mime = " + mime + "}\n"
                +"{Nombre = " + nombre + "}\n"
                +"{Archivo = " + archivo + '}';
    }
}
