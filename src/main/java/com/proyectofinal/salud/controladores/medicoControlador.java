package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.repositorios.medicoRepositorio;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import java.util.Collection;
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
@RequestMapping("/medico")
public class medicoControlador {

    @Autowired
    private medicoServicio medicoServicio;
    @Autowired
    private pacienteServicio pacienteServicio;
    @Autowired
    private medicoRepositorio medicoRepo;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
       List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
       modelo.addAttribute("ListaOS", ListaOS); 
        List<especialidad> ListaEspecialidades = medicoServicio.listadoEspecialidad();
        modelo.addAttribute("ListaEspecialidades", ListaEspecialidades);

        return "registro_medico.html";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroMedico")
    public String registroMedico(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String telefono, @RequestParam especialidad especialidad,
            @RequestParam Integer valorConsulta, @RequestParam String password,@RequestParam Collection<obraSocial> obraSocialRecibida,
            @RequestParam String password2, @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo, RedirectAttributes redireccion) {

        try {
            medicoServicio.crearMedico(nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2, archivo, obraSocialRecibida);
            redireccion.addAttribute("exito", "El profesional se registró exitosamente!");
            return "redirect:/";// queda sujeto a cambio de front. 

        } catch (Exception ex) {
            List<especialidad> ListaEspecialidades = medicoServicio.listadoEspecialidad();
            modelo.addAttribute("ListaEspecialidades", ListaEspecialidades);
            List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
            modelo.addAttribute("ListaOS", ListaOS); 
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("valorConsulta", valorConsulta);

            return "registro_medico.html";
        }
    }
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
       medico medico = (medico) session.getAttribute("usuariosession");
       
       modelo.put("medico", medico);
       List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
       modelo.addAttribute("ListaOS", ListaOS); 
        List<especialidad> ListaEspecialidades = medicoServicio.listadoEspecialidad();
        modelo.addAttribute("ListaEspecialidades", ListaEspecialidades);
        String idPersona = medico.getIdPersona();
        Collection<obraSocial> os = medicoServicio.OsRecibidas(idPersona);
       
       modelo.addAttribute("oSRecibidas", os);
       return "perfil_medico.html";
    }  
    
      @GetMapping("/modificar")
    public String modificar(ModelMap modelo,HttpSession session){
       medico medico = (medico) session.getAttribute("usuariosession");
       
       modelo.put("medico", medico);
       List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
       modelo.addAttribute("ListaOS", ListaOS); 
        List<especialidad> ListaEspecialidades = medicoServicio.listadoEspecialidad();
        modelo.addAttribute("ListaEspecialidades", ListaEspecialidades);
        String idPersona = medico.getIdPersona();
        Collection<obraSocial> os = medicoServicio.OsRecibidas(idPersona);
       
       modelo.addAttribute("oSRecibidas", os);
       return "modificar_medico.html";
    }  
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROFESIONAL')")
    @PostMapping("/perfil/{idPersona}")
    public String actualizar(@PathVariable String idPersona,@RequestParam  String nombre,@RequestParam String apellido,
            @RequestParam String email,@RequestParam  String telefono, Integer valorConsulta,
            especialidad especialidad,@RequestParam String password,
            @RequestParam String password2, MultipartFile archivo, @RequestParam Collection<obraSocial> obraSocialRecibida, ModelMap modelo, RedirectAttributes redireccion, HttpSession session) {
        
       
        try{
    
            medico medicoModificado = medicoServicio.modificarMedico(idPersona, nombre, apellido, email, telefono, valorConsulta, especialidad, password, password2, archivo, obraSocialRecibida);
            session.setAttribute("usuariosession", medicoModificado);
            modelo.put("exito", "Medico actualizado correctamente!");
        
            return "inicio.html";
        } catch (Exception ex) {
            medico medico = (medico) session.getAttribute("usuariosession");
            List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
            modelo.addAttribute("ListaOS", ListaOS);
            List<especialidad> ListaEspecialidades = medicoServicio.listadoEspecialidad();
            modelo.addAttribute("ListaEspecialidades", ListaEspecialidades);
             Collection<obraSocial> os = medico.getObraSocialRecibida();
             modelo.addAttribute("oSRecibidas", os);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("valorConsulta", valorConsulta);
            
            
            return "modificar_medico.html";
        }
    }
}
