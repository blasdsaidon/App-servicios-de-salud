package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.servicios.adminServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class adminControlador {

    @Autowired
    private adminServicio adminServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        return "registro.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroAdmin")
    public String registroAdmin(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2, @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo, RedirectAttributes redireccion) {

        try {
            adminServicio.crearAdmin(nombre, apellido, email, telefono, archivo, password, password2);
            redireccion.addAttribute("exito", "El admin se registró exitosamente!");
            return "redirect:/";// queda sujeto a cambio de front. 

        } catch (Exception ex) {
            // modelo a cambiar por front.
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";
        }
    }
}
