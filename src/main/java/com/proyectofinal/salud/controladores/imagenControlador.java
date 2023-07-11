package com.proyectofinal.salud.controladores;

<<<<<<< HEAD

=======
>>>>>>> 1e00e959c4b35c428f239209b8ad391a339d329c
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

<<<<<<< HEAD

        @GetMapping("/perfil/{idPersona}")
        public ResponseEntity<byte[]> imagenPersona(@PathVariable String idPersona, HttpSession session) {
            
             persona persona = (persona) session.getAttribute("usuariosession");
            
            byte[] imagen = persona.getImagen().getArchivo();
=======
    @GetMapping("/perfil/{idPersona}")
    public ResponseEntity<byte[]> imagenPaciente(@PathVariable String idPersona, HttpSession session) {

        persona persona = (persona) session.getAttribute("usuariosession");
>>>>>>> 1e00e959c4b35c428f239209b8ad391a339d329c

        byte[] imagen = persona.getImagen().getArchivo();

        HttpHeaders headers = new HttpHeaders();

<<<<<<< HEAD
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        }
        
//        @GetMapping("/perfilMedico/{id}")
//        public ResponseEntity<byte[]> imagenMedico(@PathVariable String id) {
//            
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
=======
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
>>>>>>> 1e00e959c4b35c428f239209b8ad391a339d329c
    }
}
