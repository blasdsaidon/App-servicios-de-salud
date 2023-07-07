package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.imagenRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class imagenServicio {

    @Autowired
    private imagenRepositorio imagenRepositorio;

    public imagen guardar(MultipartFile archivo) throws MiException {
        if (archivo.getContentType().contains("image") ) {
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

    public imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {
        if (archivo.getContentType().contains("image")) {
            try {

                imagen imagen = new imagen();

                if (idImagen != null) {
                    Optional<imagen> respuesta = imagenRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

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

    @Transactional(readOnly = true)
    public List<imagen> listarTodos() {
        return imagenRepositorio.findAll();
    }
}
