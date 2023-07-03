package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.entidades.persona;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.servicios.pacienteServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private pacienteServicio pacienteServicio;

    @GetMapping("/")
    public String inicio(ModelMap modelo, String exito) {
        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        modelo.put("exito", exito);
        return "inicio.html";
    }

    //ESTA FUNCIONA PERO NO DIFERENCIA ROLES
    /* @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {

        paciente logueado = (paciente) session.getAttribute("usuariosession");
        if (logueado != null) {
            return "redirect:/";
        }
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos.");
        }
        return "login.html";
    }*/
    
    
    // EL PRE AUTHORIZE GENERA UN ERROR (Asumo que es porque estamos intentando entrar a la vista de login y el preauthoorize
    // pide que ya estemos logueados para acceder _Leo_)
    

//  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROFESIONAL')")
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {

        try {
            persona logueado = (persona) session.getAttribute("usuariosession");
            if (logueado != null) {

                switch (logueado.getRol().toString()) {
                    case "USER":
                        return "redirect:/paciente/perfil";
                    case "ADMIN":
                        return "redirect:/admin/perfil";
                    case "PROFESIONAL":
                        return "redirect:/medico/perfil";
                }
            }
            /*if (logueado != null && logueado.getRol().toString().equals("USER")) {
            return "redirect:/paciente/perfil";
        }else if (logueado != null && logueado.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/perfil";
        }else if(logueado != null && logueado.getRol().toString().equals("PROFESIONAL")){
            return "redirect:/medico/perfil";
        }*/
            if (error != null) {
                modelo.put("error", "Usuario o Contraseña invalidos.");
            }
            return "login.html";
        } catch (Exception e) {
            return "inicio.html";
        }

    }

}
