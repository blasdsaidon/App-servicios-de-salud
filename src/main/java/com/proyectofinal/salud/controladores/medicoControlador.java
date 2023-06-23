package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.servicios.medicoServicio;
import java.util.List;
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
@RequestMapping("/medico")
public class medicoControlador {

    @Autowired
    private medicoServicio medicoServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<especialidad> ListaE = medicoServicio.listadoEspecialidad();
        modelo.addAttribute("ListaE", ListaE);

        return "registro.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroMedico")
    public String registroAdmin(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String telefono, @RequestParam especialidad especialidad,
            @RequestParam Integer valorConsulta, @RequestParam String password,
            @RequestParam String password2, @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo, RedirectAttributes redireccion) {

        try {
            medicoServicio.crearMedico(nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2, archivo);
            redireccion.addAttribute("exito", "El profesional se registró exitosamente!");
            return "redirect:/";// queda sujeto a cambio de front. 

        } catch (Exception ex) {
            List<especialidad> ListaE = medicoServicio.listadoEspecialidad();
            modelo.addAttribute("ListaE", ListaE);
            // modelo a cambiar por front.
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";
        }
    }
}
