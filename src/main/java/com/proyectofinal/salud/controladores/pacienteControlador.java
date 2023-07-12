package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.entidades.persona;
import com.proyectofinal.salud.entidades.turno;
import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import com.proyectofinal.salud.servicios.turnoServicio;
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
@RequestMapping("/paciente")
public class pacienteControlador {

    @Autowired
    private pacienteServicio pacienteServicio;

    @Autowired
    private medicoServicio medicoServicio;

    @Autowired
    private turnoServicio turnoServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
        modelo.addAttribute("ListaGenero", ListaGenero);

        return "registro.html";
    }

    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String telefono, @RequestParam(required = false) obraSocial obraSocial,
            @RequestParam(required = false) sexo genero, @RequestParam String fechaNacimiento, @RequestParam String password,
            @RequestParam String password2, @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo, RedirectAttributes redireccion) {

        try {
            pacienteServicio.crearPaciente(nombre, apellido, email, telefono, obraSocial, genero, fechaNacimiento, password, password2, archivo);
            redireccion.addAttribute("exito", "El usuario se registró exitosamente!");
            return "redirect:/";// queda sujeto a cambio de front. 

        } catch (Exception ex) {
            List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
            modelo.addAttribute("ListaOS", ListaOS);
            List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
            modelo.addAttribute("ListaGenero", ListaGenero);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("fechaNacimiento", fechaNacimiento);

            return "registro.html";
        }
    }

    @GetMapping("/sacarTurno")
    public String sacarTurno(ModelMap modelo, HttpSession session ) {
        persona persona = (persona) session.getAttribute("usuariosession");
        List<especialidad> ListaEspecialidades = medicoServicio.listadoEspecialidad();
        modelo.addAttribute("ListaEspecialidades", ListaEspecialidades);
        List<String> ginecologos = medicoServicio.listadoMedicosPorEspecialidad(especialidad.GINECOLOGIA);
        modelo.addAttribute("ListaMedicosGinecologos", ginecologos);
        List<String> clinicos = medicoServicio.listadoMedicosPorEspecialidad(especialidad.CLINICA);
        modelo.addAttribute("ListaMedicosClinicos", clinicos);
        List<String> cardiologos = medicoServicio.listadoMedicosPorEspecialidad(especialidad.CARDIOLOGIA);
        modelo.addAttribute("ListaMedicosCardiologos", cardiologos);
        List<String> pediatras = medicoServicio.listadoMedicosPorEspecialidad(especialidad.PEDIATRIA);
        modelo.addAttribute("ListaMedicosPediatras", pediatras);
        modelo.put("idPersona", persona.getIdPersona());
        return "sacar_turno.html";
    }
    
    @PostMapping("/sacarTurno/{idPersona}")
    public String confirmarTurno(@PathVariable String idPersona,String nombre, String apellido){
    
        
        
        return "inicio.html";
    }
    @GetMapping("/perfil")
    public String perfil(MultipartFile archivo, ModelMap modelo, HttpSession session) {
        
        persona persona = (persona) session.getAttribute("usuariosession");
        paciente paciente = pacienteServicio.getOne(persona.getIdPersona());
        
        modelo.put("paciente", paciente);
        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
        modelo.addAttribute("ListaGenero", ListaGenero);
        modelo.addAttribute("archivo", archivo);

        return "perfil_paciente.html";
    }

    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, HttpSession session) {
        persona persona = (persona) session.getAttribute("usuariosession");
        paciente paciente = pacienteServicio.getOne(persona.getIdPersona());
        modelo.put("paciente", paciente);
        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
        modelo.addAttribute("ListaGenero", ListaGenero);
        
        return "modificar_paciente.html";
    }

   
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROFESIONAL')")
    @PostMapping("/perfil/{idPersona}")
    public String actualizar(@PathVariable String idPersona, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String telefono, obraSocial obraSocial,
            sexo genero, String fechaNacimiento, @RequestParam String password,
            @RequestParam String password2, MultipartFile archivo, ModelMap modelo, RedirectAttributes redireccion, HttpSession session) {

        try {
            paciente pacienteModificado = pacienteServicio.modificarPaciente(idPersona, nombre, apellido, email, telefono, obraSocial, genero, fechaNacimiento, password, password2, archivo);
            session.setAttribute("usuariosession", pacienteModificado);
            modelo.put("exito", "Paciente actualizado correctamente!");

            return "redirect:/";
            
        } catch (Exception ex) {
            List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
            modelo.addAttribute("ListaOS", ListaOS);
            List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
            modelo.addAttribute("ListaGenero", ListaGenero);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("fechaNacimiento", fechaNacimiento);
            
            return "modificar_paciente.html";
        }
    }
    
    @GetMapping("/modificarTurno")
    public String modificarTurno(ModelMap modelo, HttpSession session){
        persona persona = (persona) session.getAttribute("usuariosession");
        paciente paciente = pacienteServicio.getOne(persona.getIdPersona());
        modelo.put("paciente", paciente);
        Collection<turno> Turnos = paciente.getTurnos();
        modelo.put("Turnos", Turnos);
        
        return "modificarTurnos_paciente.html";   
    }
    
    @GetMapping("/modificarTurno/{idTurno}")
    public String eliminarTurno(@PathVariable String idTurno,HttpSession session,RedirectAttributes redireccion,ModelMap modelo){
        persona persona = (persona) session.getAttribute("usuariosession");
        paciente paciente = pacienteServicio.getOne(persona.getIdPersona());
       try {
        turnoServicio.cancelarTurno(idTurno, paciente.getIdPersona());
        redireccion.addAttribute("exito", "El Turno fue eliminado con exito!");
        return "inicio.html";
       }catch(Exception ex){
        modelo.put("error", ex.getMessage());
        return "redirect:/paciente/perfil";
    }
    
}
}