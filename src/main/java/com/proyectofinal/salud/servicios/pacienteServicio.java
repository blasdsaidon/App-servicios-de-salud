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
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class pacienteServicio implements UserDetailsService{

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
            throw new MiException("El nombre no puede ser nulo o estar vacío.");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede ser nulo o estar vacío.");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío.");
        }
        if (telefono.isEmpty() || telefono == null || password.length() <= 10) {
            throw new MiException("El telefono no puede ser nulo o estar vacío.");
        }
        if (fechaNacimiento.isEmpty()) {
            throw new MiException("La fecha de nacimientoe no puede ser nula estar vacía.");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos.");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales.");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        paciente paciente = pacienteRepo.buscarPorEmail(email);
        
        if (paciente != null) {
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ paciente.getRol().toString());
            
            permisos.add(p);
   
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", paciente);
            
            return new User(paciente.getEmail(), paciente.getPassword(),permisos);
        }else{
            return null;
        }
    }
}
