package com.proyectofinal.salud.controladores;

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

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {

        persona logueado = (persona) session.getAttribute("usuariosession");
        if (logueado != null) {
            return "redirect:/";
        }
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos.");
        }
        return "login.html";
    }
}
