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
    @Lob@Basic(fetch = FetchType.LAZY)
    private byte[] archivo;

    public imagen() {
    }

    public imagen(String idImagen, byte[] archivo) {
        this.idImagen = idImagen;
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

    @Override
    public String toString() {
        return "imagen{" + "idImagen=" + idImagen + ", archivo=" + archivo + '}';
    }
}
