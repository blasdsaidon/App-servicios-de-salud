/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.imagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author luisa
 */
public class imagenServicio {
    
    @Autowired
    private imagenRepositorio imagenRepositorio;
    
    public imagen guardar(MultipartFile archivo) throws MiException{
        if (archivo != null) {
            try {
                
                imagen imagen = new imagen();
                
                imagen.setMime(archivo.getContentType());
                
                imagen.setNombre(archivo.getName());
                
                imagen.setArchivo(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
}
