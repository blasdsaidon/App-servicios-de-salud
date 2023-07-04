package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.persona;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.servicios.medicoServicio;
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

    @Autowired
    private medicoServicio medicoServicio;

    @GetMapping("/")
    public String inicio(ModelMap modelo, String exito) {

        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        modelo.put("exito", exito);
        return "inicio.html";
    }

    /*Funciona definitivamente con el la nueva redireccion por el header*/
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
            if (error != null) {
                modelo.put("error", "Usuario o Contraseña invalidos.");
            }
            return "login.html";
        } catch (Exception e) {
            return "inicio.html";
        }
    }

    @GetMapping("/listadoProfesionales")
    public String listadoProfesionales(ModelMap modelo, HttpSession session) {
        
        persona logueado = (persona) session.getAttribute("usuariosession");
        modelo.put("listadoProfesionales", medicoServicio.listarMedicos());
        modelo.put("session", logueado);

        return "listado_profesionales";
    }
}
