package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.rol;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.medicoRepositorio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class medicoServicio implements UserDetailsService {

    @Autowired
    private medicoRepositorio medicoRepo;
    @Autowired
    private imagenServicio imagenServicio;

    @Transactional
    public void crearMedico(String nombre, String apellido, String email, String telefono,
            Integer valorConsulta, especialidad especialidad, String password,
            String password2, MultipartFile archivo) throws MiException, ParseException {

        medico medico = new medico();
        validar(nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2);

        medico.setApellido(apellido);
        medico.setNombre(nombre);
        medico.setEmail(email);
        medico.setTelefono(telefono);
        medico.setValorConsulta(valorConsulta);
        medico.setRol(rol.PROFESIONAL);
        medico.setPassword(new BCryptPasswordEncoder().encode(password));
        imagen imagen = imagenServicio.guardar(archivo);
        medico.setImagen(imagen);
        medico.setEspecialidad(especialidad);
        medicoRepo.save(medico);
    }

    @Transactional
    public void cargarObrasSociales(String idMedico, obraSocial obraSocial) {

        Optional<medico> respuesta = medicoRepo.findById(idMedico);
        medico medico = respuesta.get();

        if (respuesta.isPresent()) {
            medico.setObraSocialRecibida(obraSocial);
            medicoRepo.save(medico);
        }
    }

    @Transactional
    public void darDeBaja(String idMedico, Boolean alta) {

        Optional<medico> respuesta = medicoRepo.findById(idMedico);
        medico medico = respuesta.get();
        if (respuesta.isPresent()) {

            medicoRepo.save(medico);
        }
    }

    @Transactional
    public void modificarMedico(String idMedico, String nombre, String apellido, String email, String telefono,
            Integer valorConsulta, especialidad especialidad, String password, String password2,
            MultipartFile archivo) throws MiException, ParseException {

        medico medico = new medico();
        validar(nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2);

        Optional<medico> respuesta = medicoRepo.findById(idMedico);

        if (respuesta.isPresent()) {
            medico.setApellido(apellido);
            medico.setNombre(nombre);
            medico.setEmail(email);
            medico.setTelefono(telefono);
            medico.setValorConsulta(valorConsulta);
            medico.setEspecialidad(especialidad);
            medico.setRol(rol.PROFESIONAL);
            medico.setPassword(new BCryptPasswordEncoder().encode(password));
            /* imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen);*/

            medicoRepo.save(medico);
        }
    }

    @Transactional
    public void eliminar(String idMedico) throws MiException {

        medico medico = medicoRepo.getById(idMedico);

        medicoRepo.delete(medico);
    }

    @Transactional(readOnly = true)
    public List<medico> listarMedicos() {

        List<medico> medicos = new ArrayList();

        medicos = medicoRepo.findAll();

        return medicos;
    }

    @Transactional(readOnly = true)
    public medico getOne(String idMedico) {

        return medicoRepo.getOne(idMedico);
    }

    public List listadoObrasSocial() {

        obraSocial[] vectorOS = obraSocial.values();
        List<obraSocial> ListaOS = new ArrayList();
        ListaOS.addAll(Arrays.asList(vectorOS));

        return ListaOS;
    }

    public List listadoEspecialidad() {

        especialidad[] vectorE = especialidad.values();
        List<especialidad> ListaE = new ArrayList();
        ListaE.addAll(Arrays.asList(vectorE));

        return ListaE;
    }

    public medico buscarMedicoPorEmail(String email) {

        medico medico = medicoRepo.buscarPorEmail(email);

        return medico;
    }

    public medico buscarMedicoPorTelefono(String telefono) {

        medico medico = medicoRepo.buscarPorTelefono(telefono);

        return medico;
    }

    public void validar(String nombre, String apellido, String email, String telefono,
            Integer valorConsulta, especialidad especialidad, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío.");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede ser nulo o estar vacío.");
        }
        if (email.isEmpty() || email == null || buscarMedicoPorEmail(email) != null) {
            throw new MiException("El email no puede ser nulo o estar vacío.");
        }
        if (telefono.isEmpty() || telefono == null || telefono.length() != 10 || buscarMedicoPorTelefono(telefono) != null) {
            throw new MiException("El telefono no puede ser nulo, estar vacío, y debe contener 10 carácteres.");
        }
        if (valorConsulta == null) {
            throw new MiException("El valor de consulta no puede ser nulo o estar vacío.");
        }
        if (especialidad == null) {
            throw new MiException("La especialidad no puede ser nula o estar vacía.");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener 5 o más digitos.");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        medico medico = medicoRepo.buscarPorEmail(email);

        if (medico != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + medico.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", medico);

            return new User(medico.getEmail(), medico.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
