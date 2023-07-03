package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class imagenControlador {

    @Controller
    @RequestMapping("/imagen")
    public class ImagenControlador {

        @Autowired
        pacienteServicio pacienteServicio;
        
        @Autowired
        medicoServicio medicoServicio;

        @GetMapping("/perfil/{idPersona}")
        public ResponseEntity<byte[]> imagenPaciente(@PathVariable String id) {
            paciente paciente = pacienteServicio.getOne(id);

            byte[] imagen = paciente.getImagen().getArchivo();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        }
        
//        @GetMapping("/perfilMedico/{id}")
//        public ResponseEntity<byte[]> imagenMedico(@PathVariable String id) {
//            medico medico = medicoServicio.getOne(id);
//
//            byte[] imagen = medico.getImagen().getArchivo();
//
//            HttpHeaders headers = new HttpHeaders();
//
//            headers.setContentType(MediaType.IMAGE_JPEG);
//
//            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
//        }
    }
}
