package com.proyectofinal.salud.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class portalControlador {

    @GetMapping("/")
    public String inicio(ModelMap modelo, String exito) {
        modelo.put("exito", exito);
        return "inicio.html";
    }
}
