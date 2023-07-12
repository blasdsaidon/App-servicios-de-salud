package com.proyectofinal.salud.controladores;

import com.proyectofinal.salud.entidades.medico;
import com.proyectofinal.salud.entidades.persona;
import com.proyectofinal.salud.enumeradores.especialidad;
import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.servicios.medicoServicio;
import com.proyectofinal.salud.servicios.pacienteServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private pacienteServicio pacienteServicio;

    @Autowired
    private medicoServicio medicoServicio;

    @GetMapping("/")
    public String inicio(ModelMap modelo, String exito) {

        List<obraSocial> ListaOS = pacienteServicio.listadoObrasSocial();
        modelo.addAttribute("ListaOS", ListaOS);
        modelo.put("exito", exito);

        return "inicio.html";
    }

    /*Funciona definitivamente con el la nueva redireccion por el header*/
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {

        try {
            persona logueado = (persona) session.getAttribute("usuariosession");
            if (logueado != null) {

                switch (logueado.getRol().toString()) {
                    case "USER":
                        return "redirect:/paciente/perfil";
                    case "ADMIN":
                        return "redirect:/admin/perfil";
                    case "PROFESIONAL":
                        return "redirect:/medico/perfil";
                }
            }

            if (error != null) {
                modelo.put("error", "Usuario o Contraseña invalidos.");
            }

            return "login.html";

        } catch (Exception e) {

            return "inicio.html";
        }
    }

    @GetMapping("/listadoProfesionales")
    public String listadoProfesionales(ModelMap modelo, HttpSession session) {

        modelo.put("listadoProfesionales", medicoServicio.listarMedicos());

        return "listado_profesionales";
    }
    
    @GetMapping("/obrasSociales")
    public String obrasSociales(ModelMap modelo, HttpSession session){
    
    return "obras_sociales.html";
    }

    @PostMapping("/profesionBuscada")
    public String profesionBuscada(String buscar, ModelMap modelo) {

        if (buscar == null || buscar.isEmpty()) {
            modelo.put("listadoProfesionales", medicoServicio.listarMedicos());

        } else {
            if (buscar.equalsIgnoreCase("PEDIATRIA") || buscar.equalsIgnoreCase("GINECOLOGIA")
                    || buscar.equalsIgnoreCase("CLINICA") || buscar.equalsIgnoreCase("CARDIOLOGIA")) {
                especialidad especialidad = null;

                try {
                    especialidad = especialidad.valueOf(buscar.toUpperCase());
                    List<medico> busqueda = medicoServicio.buscarMedicoPorEspecialidad(especialidad);
                    modelo.put("listadoProfesionales", busqueda);

                } catch (Exception e) {
                    modelo.put("error", "La especialidad buscada no existe.");

                } finally {
                    return "listado_profesionales.html";
                }

            } else {
                String nombre = null;

                try {
                    nombre = nombre.valueOf(buscar.toUpperCase());
                    List<medico> busqueda = medicoServicio.buscarMedicoPorNombre(nombre);
                    modelo.put("listadoProfesionales", busqueda);

                } catch (Exception e) {
                    modelo.put("error", "El nombre del profesional buscado no existe.");

                } finally {
                    return "listado_profesionales.html";
                }
            }
        }

        return "listado_profesionales.html";
    }

    @GetMapping("/obrasSociales")
    public String obrasSociales() {

        return "obras_sociales.html";
    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros() {

        return "sobre_nosotros.html";
    }
}