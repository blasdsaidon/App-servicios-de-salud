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
public class pacienteServicio implements UserDetailsService {

    @Autowired
    private pacienteRepositorio pacienteRepo;
    @Autowired
    private imagenServicio imagenServicio;

    @Transactional
    public void crearPaciente(String nombre, String apellido, String email, String telefono,
            obraSocial obraSocial, sexo genero, String fechaNacimiento, String password, String password2,
            MultipartFile archivo) throws MiException, ParseException {

        paciente paciente = new paciente();
        validar(nombre, apellido, email, telefono, fechaNacimiento, password, password2, true);

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
    public paciente modificarPaciente(String idPersona, String nombre, String apellido, String email, String telefono,
            obraSocial obraSocial, sexo genero, String fechaNacimiento, String password, String password2,
            MultipartFile archivo) throws MiException, ParseException {

        
        validar(nombre, apellido, email, telefono, fechaNacimiento, password, password2, false);
        
        Date fechaNacimientoDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
        
        Optional<paciente> respuesta = pacienteRepo.findById(idPersona);
        paciente paciente=new paciente();
        if (respuesta.isPresent()) {
            paciente = respuesta.get();
            System.out.println(paciente.toString());
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
        return paciente;
    }

    @Transactional
    public void eliminar(String idPaciente) throws MiException {

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
    public paciente getOne(String idPaciente) {
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

    public paciente buscarPacientePorEmail(String email) {

        paciente paciente = pacienteRepo.buscarPorEmail(email);

        return paciente;
    }

    public paciente buscarPacientePorTelefono(String telefono) {

        paciente paciente = pacienteRepo.buscarPorTelefono(telefono);

        return paciente;
    }

    public void validar(String nombre, String apellido, String email, String telefono,
            String fechaNacimiento, String password, String password2, boolean esNuevoUsuario) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre ingresado no puede ser nulo o estar vacío.");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido ingresado no puede ser nulo o estar vacío.");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email ingresado no puede ser nulo o estar vacío.");
        } else if (esNuevoUsuario==true){
            
          if (buscarPacientePorEmail(email) != null) {
            throw new MiException("El email ingresado ya se encuentra registrado.");
          }
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El número télefono ingresado no puede ser nulo o estar vacío.");
        
        } else if (telefono.length() != 10) {
            throw new MiException("El número de teléfono ingresado debe contener 10 caracteres.");
        }   else if (esNuevoUsuario==true){
              if (buscarPacientePorTelefono(telefono) != null) {
            throw new MiException("El número de teléfono ingresado ya se encuentra registrado.");
              }
          }
        
        if (fechaNacimiento.isEmpty()) {
            throw new MiException("La fecha de nacimiento ingresada no puede ser nula o estar vacía.");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña ingresada no puede ser nula o estar vacía.");
        } else if (password.length() < 5) {
            throw new MiException("La contraseña ingresada debe tener 5 digitos o más.");
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

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + paciente.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", paciente);

            return new User(paciente.getEmail(), paciente.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
