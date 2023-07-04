package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.admin;
import com.proyectofinal.salud.servicios.adminServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        return "registroAdmin.html";
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
            
            return "redirect:/";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registroAdmin.html";
        }
    }
    
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session) {
        
       admin admin = (admin) session.getAttribute("usuariosession");
       modelo.put("admin", admin);

       return "perfil_admin.html";
    }  
    
      @GetMapping("/modificar")
    public String modificar(ModelMap modelo,HttpSession session) {
        
       admin admin = (admin) session.getAttribute("usuariosession");
       modelo.put("admin", admin);
       
       return "modificar_admin.html";
    }  
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROFESIONAL')")
    @PostMapping("/perfil/{idPersona}")
    public String actualizar(@PathVariable String idPersona,@RequestParam  String nombre,@RequestParam String apellido,
            @RequestParam String email,@RequestParam  String telefono, @RequestParam String password,
            @RequestParam String password2,  MultipartFile archivo, ModelMap modelo, RedirectAttributes redireccion, HttpSession session) {
        
        try{
            admin adminModificado = adminServicio.modificarAdmin(idPersona, nombre, apellido, email, telefono, archivo, password, password2);
            session.setAttribute("usuariosession", adminModificado);
            modelo.put("exito", "Admin actualizado correctamente!");
        
            return "inicio.html";
            
        } catch (Exception ex) {
            admin admin = (admin) session.getAttribute("usuariosession");
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            
            return "modificar_admin.html";
        }
    }
}
