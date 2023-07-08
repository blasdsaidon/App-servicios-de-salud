package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.persona;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class imagenControlador {

    @GetMapping("/perfil/{idPersona}")
    public ResponseEntity<byte[]> imagenPaciente(@PathVariable String idPersona, HttpSession session) {

        persona persona = (persona) session.getAttribute("usuariosession");

        byte[] imagen = persona.getImagen().getArchivo();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}
