/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.salud.servicios;

import com.proyectofinal.salud.entidades.persona;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class personaServicio implements UserDetailsService {

    @Autowired
    public medicoServicio medicoServicio;
    
    
    @Autowired
    public pacienteServicio pacienteServicio;
    
    @Autowired
    public adminServicio adminServicio;
    
    
    
   @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
        persona persona = null;   
       if (medicoServicio.buscarMedicoPorEmail(email)!= null ) {
            persona = medicoServicio.buscarMedicoPorEmail(email);
      }else if (pacienteServicio.buscarPacientePorEmail(email)!=null) {
            persona = pacienteServicio.buscarPacientePorEmail(email);   
       } else if (adminServicio.buscarAdminPorEmail(email)!=null){
            persona = adminServicio.buscarAdminPorEmail(email);
       }
        
       

        if (persona != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + persona.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", persona);

            return new User(persona.getEmail(), persona.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
