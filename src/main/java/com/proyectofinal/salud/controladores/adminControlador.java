package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.admin;
import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.servicios.adminServicio;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import com.proyectofinal.salud.servicios.turnoServicio;
import java.util.List;
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

    @Autowired
    private pacienteServicio pacienteServicio;

    @Autowired
    private medicoServicio medicoServicio;

    @Autowired
    private turnoServicio turnoServicio;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
        modelo.addAttribute("ListaGenero", ListaGenero);

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        admin admin = (admin) session.getAttribute("usuariosession");
        modelo.put("admin", admin);

        return "perfil_admin.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, HttpSession session) {

        admin admin = (admin) session.getAttribute("usuariosession");
        modelo.put("admin", admin);

        return "modificar_admin.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/perfil/{idPersona}")
    public String actualizar(@PathVariable String idPersona, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String telefono, @RequestParam String password,
            @RequestParam String password2, MultipartFile archivo, ModelMap modelo, RedirectAttributes redireccion, HttpSession session) {

        try {
            admin adminModificado = adminServicio.modificarAdmin(idPersona, nombre, apellido, email, telefono, archivo, password, password2);
            session.setAttribute("usuariosession", adminModificado);
            modelo.put("exito", "El admin se ha actualizado correctamente!");

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/estado/{idPersona}")
    public String estado(@PathVariable String idPersona, ModelMap modelo, RedirectAttributes redireccion) {

        medico medico = medicoServicio.buscarMedicoPorID(idPersona);

        try {
            medicoServicio.estado(idPersona);
            redireccion.addAttribute("exito", "El estado del profesional fue modificado con exito!");

            return "redirect:/listadoProfesionales";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

            return "redirect:/listadoProfesionales";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/eliminarProfesional/{idPersona}")
    public String eliminacionMedico(@PathVariable String idPersona, ModelMap modelo, RedirectAttributes redireccion) {

        modelo.put("medico", medicoServicio.buscarMedicoPorID(idPersona));

        try {
            medicoServicio.vaciarTurnosMedicos(idPersona);
            turnoServicio.eliminarTurnosTotales(idPersona);
            medicoServicio.eliminarMedico(idPersona);
            redireccion.addAttribute("exito", "El profesional fue eliminado con exito!");

            return "redirect:/";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());

            return "redirect:/listadoProfesionales";
        }
    }
}