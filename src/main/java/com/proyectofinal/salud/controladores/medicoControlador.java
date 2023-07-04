package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.repositorios.medicoRepositorio;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import java.util.Collection;
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

    @GetMapping("/sacarTurno")
    public String sacarTurno(ModelMap modelo) {
        
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
        
        return "turnos.html";
    }
}
