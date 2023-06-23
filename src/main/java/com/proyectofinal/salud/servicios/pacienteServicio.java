package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.rol;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.pacienteRepositorio;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class pacienteServicio {

    @Autowired
    private pacienteRepositorio pacienteRepo;

    @Autowired
    private imagenServicio imagenServicio;

    @Transactional
    public void crearPaciente(String nombre, String apellido, String email, String telefono,
            obraSocial obraSocial, sexo genero, Date fechaNacimiento, String password, String password2,
            MultipartFile archivo) throws MiException {

        paciente paciente = new paciente();

        validar(nombre, apellido, email, telefono, obraSocial, genero, fechaNacimiento, password, password2);

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

    @Transactional
    public void modificarPaciente(String idPaciente, String nombre, String apellido, String email, String telefono,
            obraSocial obraSocial, sexo genero, Date fechaNacimiento, String password, String password2,
            MultipartFile archivo) throws MiException {

        paciente paciente = new paciente();

        validar(nombre, apellido, email, telefono, obraSocial, genero, fechaNacimiento, password, password2);

        Optional<paciente> respuesta = pacienteRepo.findById(idPaciente);

        if (respuesta.isPresent()) {
            paciente.setApellido(apellido);
            paciente.setNombre(nombre);
            paciente.setEmail(email);
            paciente.setTelefono(telefono);
            paciente.setObraSocial(obraSocial);
            paciente.setGenero(genero);
            paciente.setRol(rol.USER);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));
            /* imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen);*/
            paciente.setFechaNacimiento(fechaNacimiento);
            pacienteRepo.save(paciente);
        }      
    }

    @Transactional
    public void eliminar(String idPaciente) throws MiException{
        
        paciente paciente = pacienteRepo.getById(idPaciente);
        
        pacienteRepo.delete(paciente);
    }
    
    @Transactional(readOnly = true)
    public List<paciente> listarPacientes() {

        List<paciente> pacientes = new ArrayList();

        pacientes = pacienteRepo.findAll();

        return pacientes;
    }
    
    @Transactional(readOnly = true)
    public paciente getOne(String idPaciente){
       return pacienteRepo.getOne(idPaciente);
    }

    public List listadoObrasSocial() {
        obraSocial[] vectorOS = obraSocial.values();
        List<obraSocial> ListaOS = new ArrayList();
        ListaOS.addAll(Arrays.asList(vectorOS));
        return ListaOS;
    }

    public List listadoGeneros() {
        sexo[] vectorsexo = sexo.values();
        List<sexo> ListaGenero = new ArrayList();
        ListaGenero.addAll(Arrays.asList(vectorsexo));
        return ListaGenero;
    }

    public void validar(String nombre, String apellido, String email, String telefono,
            obraSocial obraSocial, sexo genero, Date fechaNacimiento, String password,
            String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("el apellido no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacío");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("el telefono no puede ser nulo o estar vacío");
        }
        if (obraSocial == null) {
            throw new MiException("la obra social no puede ser nula, si no tiene obra social, elija la opción -no tiene-");
        }
        if (genero == null) {
            throw new MiException("el genero no puede ser nulo");
        }
        if (fechaNacimiento.isEmpty() || fechaNacimiento == null) {
            throw new MiException("la fecha de nacimiento no puede ser nula");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }
}
