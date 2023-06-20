/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.salud.controladores;


import com.proyectofinal.salud.enumeradores.obraSocial;
import com.proyectofinal.salud.enumeradores.sexo;
import com.proyectofinal.salud.excepciones.MiException;
import com.proyectofinal.salud.servicios.pacienteServicio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class portalControlador {
    
    @Autowired
    private pacienteServicio pacienteServicio;
    
    @GetMapping("/")
    public String inicio(){
        return "inicio.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registro.html";
    }
    
    @PostMapping("/registroPaciente")
    public String registroPaciente(@RequestParam String nombre,@RequestParam String apellido,
            @RequestParam String email,@RequestParam String telefono,@RequestParam obraSocial obraSocial,
            @RequestParam sexo genero,@RequestParam Date fechaNacimiento,@RequestParam String password,
            @RequestParam String password2, MultipartFile archivo, ModelMap modelo ){
    
         try {
            pacienteServicio.crearPaciente(nombre, apellido, email, telefono, obraSocial, genero, fechaNacimiento, password, password2, archivo);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "index.html";// queda sujeto a cambio de front. 
        } catch (MiException ex) {
            // modelo a cambiar por front.
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";
        }
       
    }

}
