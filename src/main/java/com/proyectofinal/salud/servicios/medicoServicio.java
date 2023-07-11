package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.rol;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.medicoRepositorio;
import com.proyectofinal.salud.repositorios.turnoRepositorio;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class medicoServicio /*implements UserDetailsService */{

    @Autowired
    private medicoRepositorio medicoRepo;
    @Autowired
    private imagenServicio imagenServicio;

    @Autowired
    private turnoRepositorio turnoRepo;

    @Transactional
    public void crearMedico(String nombre, String apellido, String email, String telefono,
            Integer valorConsulta, especialidad especialidad, String password,
            String password2, MultipartFile archivo, Collection<obraSocial> obraSocialRecibida) throws MiException, ParseException {

        medico medico = new medico();
        validar(nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2, obraSocialRecibida, true);

        medico.setObraSocialRecibida(obraSocialRecibida);
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
        medico.setAlta(Boolean.TRUE);
        medicoRepo.save(medico);
    }

//    @Transactional
//    public void cargarObrasSociales(obraSocial idMedico, obraSocial obraSocial) {
//
//        Optional<medico> respuesta = medicoRepo.findById(idMedico);
//        medico medico = respuesta.get();
//
//        if (respuesta.isPresent()) {
//            medico.setObraSocialRecibida(obraSocial);
//            medicoRepo.save(medico);
//        }
//    }
    @Transactional
    public void darDeBajaYAlta(String idMedico) {

        Optional<medico> respuesta = medicoRepo.findById(idMedico);
        medico medico = respuesta.get();
        if (medico.getAlta()) {
            medico.setAlta(Boolean.FALSE);
            medicoRepo.save(medico);
        }else{
            medico.setAlta(Boolean.TRUE);
            medicoRepo.save(medico);
        }
    }

    @Transactional
    public medico modificarMedico(String idMedico, String nombre, String apellido, String email, String telefono,
            Integer valorConsulta, especialidad especialidad, String password, String password2,
            MultipartFile archivo, Collection<obraSocial> obraSocialRecibida) throws MiException, ParseException {

        medico medico = new medico();
        validar(nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2, obraSocialRecibida, false);

        Optional<medico> respuesta = medicoRepo.findById(idMedico);

        if (respuesta.isPresent()) {
            medico = respuesta.get();
            medico.setApellido(apellido);
            medico.setNombre(nombre);
            medico.setEmail(email);
            medico.setTelefono(telefono);
            medico.setValorConsulta(valorConsulta);
            medico.setEspecialidad(especialidad);
            medico.setRol(rol.PROFESIONAL);
            medico.setObraSocialRecibida(obraSocialRecibida);
            medico.setPassword(new BCryptPasswordEncoder().encode(password));
            medico.setImagen(respuesta.get().getImagen());
            /* imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen);*/
            
            String idImagen = null;

            if (medico.getImagen() != null) {
                idImagen = medico.getImagen().getIdImagen();
            }

            imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            medico.setImagen(imagen);

            medicoRepo.save(medico);
        }
        return medico;
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

    @Transactional
    public Collection OsRecibidas(String idPersona) {
        medico medico = medicoRepo.getById(idPersona);
        Collection<obraSocial> os = medico.getObraSocialRecibida();

        return os;
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

    public List listadoEspecialidad() {

        especialidad[] vectorEspecialidades = especialidad.values();
        List<especialidad> ListaEspecialidades = new ArrayList();
        ListaEspecialidades.addAll(Arrays.asList(vectorEspecialidades));

        return ListaEspecialidades;
    }

    public List<String> listadoMedicosPorEspecialidad(especialidad especialidad) {

        List<String> medicos = new ArrayList();
        List<medico> profesionales = medicoRepo.buscarNombresPorEspecialidad(especialidad);
        for (medico profesional : profesionales) {
            medicos.add(profesional.getNombre() + " " + profesional.getApellido());
        }

        return medicos;
    }

    public medico buscarMedicoPorEmail(String email) {

        medico medico = medicoRepo.buscarPorEmail(email);

        return medico;
    }

    public medico buscarMedicoPorTelefono(String telefono) {

        medico medico = medicoRepo.buscarPorTelefono(telefono);

        return medico;
    }

    public medico buscarMedicoPorID(String idPersona) {

        medico medico = medicoRepo.buscarMedicoPorID(idPersona);

        return medico;
    }

    public void validar(String nombre, String apellido, String email, String telefono,
            Integer valorConsulta, especialidad especialidad, String password, String password2, Collection<obraSocial> obraSocialRecibida, boolean esNuevoUsuario) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre ingresado no puede ser nulo o estar vacío.");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido ingresado no puede ser nulo o estar vacío.");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email ingresado no puede ser nulo o estar vacío.");
        } else if (esNuevoUsuario == true) {

            if (buscarMedicoPorEmail(email) != null) {
                throw new MiException("El email ingresado ya se encuentra registrado.");
            }
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El número télefono ingresado no puede ser nulo o estar vacío.");

        } else if (telefono.length() != 10) {
            throw new MiException("El número de teléfono ingresado debe contener 10 caracteres.");
        } else if (esNuevoUsuario == true) {
            if (buscarMedicoPorTelefono(telefono) != null) {
                throw new MiException("El número de teléfono ingresado ya se encuentra registrado.");
            }
        }
        if (valorConsulta == null) {
            throw new MiException("El valor de consulta ingresado no puede ser nulo o estar vacío.");
        } else if (valorConsulta < 0) {
            throw new MiException("El valor de consulta ingresado debe ser positivo.");
        }
        if (especialidad == null) {
            throw new MiException("La especialidad ingresada no puede ser nula o estar vacía.");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nula o estar vacía.");
        } else if (password.length() < 5) {
            throw new MiException("La contraseña ingresada debe tener 5 digitos o más.");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales.");
        }
        if (obraSocialRecibida.isEmpty()) {
            throw new MiException("Si no acepta obras sociales seleccione " + "NINGUNA");
        }
    }
/*
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
    }*/
}
