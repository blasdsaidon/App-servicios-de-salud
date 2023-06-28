package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.admin;
import com.proyectofinal.salud.entidades.imagen;
import com.proyectofinal.salud.enumeradores.rol;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.repositorios.adminRepositorio;
import java.util.ArrayList;
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
public class adminServicio implements UserDetailsService {

    @Autowired
    private adminRepositorio adminRepo;
    
    @Autowired
    private imagenServicio imagenServicio;

//    @Transactional
//    public void crearAdmin(String nombre, String apellido, String email,
//            String telefono, MultipartFile archivo, String password, String password2) throws MiException {
//
//        admin admin = new admin();
//        validar(nombre, apellido, email, telefono, password, password2);
//
//        admin.setNombre(nombre);
//        admin.setApellido(apellido);
//        admin.setEmail(email);
//        admin.setTelefono(telefono);
//        imagen imagen = imagenServicio.guardar(archivo);
//        admin.setRol(rol.ADMIN);
//        admin.setImagen(imagen);
//        admin.setPassword(new BCryptPasswordEncoder().encode(password));
//        adminRepo.save(admin);
//    }

    @Transactional
    public void modificarAdmin(String idAdmin, String nombre, String apellido, String email,
            String telefono, MultipartFile archivo, String password, String password2) throws MiException {

        admin admin = new admin();
        validar(nombre, apellido, email, telefono, password, password2);
        Optional<admin> respuesta = adminRepo.findById(idAdmin);

        if (respuesta.isPresent()) {
            admin.setNombre(nombre);
            admin.setApellido(apellido);
            admin.setEmail(email);
            admin.setTelefono(telefono);
            imagen imagen = imagenServicio.guardar(archivo);
            admin.setRol(rol.ADMIN);
            admin.setImagen(imagen);
            admin.setPassword(new BCryptPasswordEncoder().encode(password));
            adminRepo.save(admin);
        }
    }

    public admin buscarAdminPorEmail(String email) {

        admin admin = adminRepo.buscarPorEmail(email);

        return admin;
    }

    public admin buscarAdminPorTelefono(String telefono) {

        admin admin = adminRepo.buscarPorTelefono(telefono);

        return admin;
    }
    
    public void validar(String nombre, String apellido, String email, String telefono,
            String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre ingresado no puede ser nulo o estar vacío.");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido ingresado no puede ser nulo o estar vacío.");
        } 
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío.");
        } else if (buscarAdminPorEmail(email) != null) {
            throw new MiException("El email ingresado ya se encuentra registrado.");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El número de teléfono no puede ser nulo o estar vacío.");
        } else if (buscarAdminPorTelefono(telefono) != null) {
            throw new MiException("El número de teléfono ingresado ya se encuentra registrado.");
        } else if (telefono.length() != 10) {
            throw new MiException("El número de teléfono ingresado debe contener 10 caracteres.");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nula o estar vacía.");
        } else if (password.length() < 5) {
            throw new MiException("La contraseña ingresada debe tener 5 digitos o más.");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        admin admin = adminRepo.buscarPorEmail(email);

        if (admin != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + admin.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", admin);

            return new User(admin.getEmail(), admin.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
