package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.servicios.adminServicio;
import com.proyectofinal.salud.servicios.medicoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminControlador {

    @Autowired
    private adminServicio adminServicio;

    @Autowired
    private medicoServicio medicoServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        return "registroAdmin.html";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/estado/{idPersona}")
//    public String estado(@PathVariable String idPersona, ModelMap modelo, RedirectAttributes redireccion) {
//        medico medico = medicoServicio.buscarMedicoPorID(idPersona);
//
//        try {
//
//            medicoServicio.estado(idPersona);
////            redireccion.addFlashAttribute("exito", "La noticia fue eliminada con exito!");
//            redireccion.addAttribute("exito", "El estado del medico fue modificado con exito!");
//
//            return "redirect:/listadoProfesionales";
//
//        } catch (Exception ex) {
//
//            modelo.put("error", ex.getMessage());
//
//            return "/listadoProfesionales";
//        }
//    }
    
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/registroAdmin")
//    public String registroAdmin(@RequestParam String nombre, @RequestParam String apellido,
//            @RequestParam String email, @RequestParam String telefono, @RequestParam String password,
//            @RequestParam String password2, @RequestParam(required = false) MultipartFile archivo,
//            ModelMap modelo, RedirectAttributes redireccion) {
//
//        try {
//            adminServicio.crearAdmin(nombre, apellido, email, telefono, archivo, password, password2);
//            redireccion.addAttribute("exito", "El admin se registró exitosamente!");
//            return "redirect:/";// queda sujeto a cambio de front. 
//
//        } catch (Exception ex) {
//            // modelo a cambiar por front.
//            modelo.put("error", ex.getMessage());
//            modelo.put("nombre", nombre);
//            modelo.put("email", email);
//
//            return "registroAdmin.html";
//        }
//    }
}
