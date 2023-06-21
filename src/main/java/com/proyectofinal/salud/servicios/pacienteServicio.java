package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.rol;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.pacienteRepositorio;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class pacienteServicio {
    
    @Autowired
    private pacienteRepositorio pacienteRepo;
    private imagenServicio imagenServicio;
    
    @Transactional
    public void crearPaciente(String nombre, String apellido, String email, String telefono, 
            obraSocial obraSocial, sexo genero, Date fechaNacimiento, String password, String password2, 
            MultipartFile archivo) throws MiException{
        
        paciente paciente = new paciente();
        
        paciente.setApellido(apellido);
        paciente.setNombre(nombre);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setObraSocial(obraSocial);
        paciente.setGenero(genero);
        paciente.setRol(rol.USER);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);
        paciente.setFechaNacimiento(fechaNacimiento); 
        pacienteRepo.save(paciente);
    }
    
    public List listadoObrasSocial(){
        obraSocial[] vectorOS = obraSocial.values();
        List<obraSocial> ListaOS = null;
        for (obraSocial social : vectorOS) {
            ListaOS.add(social);
        }       
        return ListaOS;
    }
}
