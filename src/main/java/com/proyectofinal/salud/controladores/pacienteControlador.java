package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.paciente;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.servicios.pacienteServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
     
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        
        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
        modelo.addAttribute("ListaGenero", ListaGenero);
        
        return "registro.html";
    }
    
    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre,@RequestParam String apellido,
            @RequestParam String email,@RequestParam String telefono,@RequestParam(required = false) obraSocial obraSocial,
            @RequestParam(required = false) sexo genero,@RequestParam String fechaNacimiento,@RequestParam String password,
            @RequestParam String password2,@RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo, RedirectAttributes redireccion){
    
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
   /*Se añade controlador para modificar pacientes*/ 
     @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
       paciente paciente = (paciente) session.getAttribute("usuariosession");
       modelo.put("persona", paciente);
       List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
       modelo.addAttribute("ListaOS", ListaOS); 
       List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
       modelo.addAttribute("ListaGenero", ListaGenero);
       
       
       return "panelPaciente.html";
    }  
  
    @GetMapping("/perfil/modificar/{idPaciente}")
    public String modificar(@PathVariable String idPaciente, ModelMap modelo){
            
            modelo.put("paciente", pacienteServicio.getOne(idPaciente));
            List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
            modelo.addAttribute("ListaOS", ListaOS);
            List<sexo> ListaGenero = pacienteServicio.listadoGeneros();
            modelo.addAttribute("ListaGenero", ListaGenero);
        
        return "modificarPaciente.html";  
    }
    
    @PostMapping("/perfil/modificar/{idPaciente}")
    public String modificar(@PathVariable String idPaciente,@RequestParam  String nombre,@RequestParam String apellido,
            @RequestParam String email,@RequestParam  String telefono, obraSocial obraSocial,
            sexo genero, String fechaNacimiento,@RequestParam String password,
            @RequestParam String password2, MultipartFile archivo, ModelMap modelo, RedirectAttributes redireccion){
        
        try {
            pacienteServicio.modificarPaciente(idPaciente, nombre, apellido, email, telefono, obraSocial, genero, fechaNacimiento, password, password2, archivo);
            redireccion.addAttribute("exito", "El usuario se registró exitosamente!");
            return "redirect:/";// queda sujeto a cambio de front. 
            
        
        }catch (Exception ex){
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
            return "modificarPaciente.html";/*suejeto a cambio de front*/
        }
    
    
    }
}
