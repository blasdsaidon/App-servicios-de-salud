package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.rol;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.pacienteRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            obraSocial obraSocial, sexo genero, String fechaNacimiento, String password, String password2,
            MultipartFile archivo) throws MiException, ParseException {

        paciente paciente = new paciente();
        validar(nombre, apellido, email, telefono, fechaNacimiento, password, password2);
        Date fechaNacimientoDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
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
        paciente.setFechaNacimiento(fechaNacimientoDate);
        pacienteRepo.save(paciente);
    }

    @Transactional
    public void modificarPaciente(String idPaciente, String nombre, String apellido, String email, String telefono,
            obraSocial obraSocial, sexo genero, String fechaNacimiento, String password, String password2,
            MultipartFile archivo) throws MiException, ParseException {

        paciente paciente = new paciente();
        validar(nombre, apellido, email, telefono, fechaNacimiento, password, password2);
        Date fechaNacimientoDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
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
            paciente.setFechaNacimiento(fechaNacimientoDate);
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
    
    public paciente buscarMedicoporEmail(String email){
        paciente paciente = pacienteRepo.buscarPorEmail(email);
        return paciente;
    }

    public void validar(String nombre, String apellido, String email, String telefono,
            String fechaNacimiento, String password,String password2) throws MiException {

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
        if (fechaNacimiento.isEmpty()) {
            throw new MiException("la fecha de nacimientoe no puede ser nula estar vacía");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }
}
