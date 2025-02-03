package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.ticketerra_web.modelo.Usuario;
import com.proyecto.ticketerra_web.servicio.UsuarioServicio;

@Controller
@RequestMapping("/usuarios")
public class RegistrarControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public RegistrarControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/registrar")
    public String registrarForm() {
        return "registrar"; 
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String nombreCompleto, 
                                   @RequestParam String correo,
                                   @RequestParam String telefono, 
                                   @RequestParam String codigoPostal, 
                                   @RequestParam String contrasena, 
                                   @RequestParam String repetirContrasena, 
                                   Model model) {
    	
       

        if (!contrasena.equals(repetirContrasena)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "registrar";
        }

        Usuario usuario = new Usuario(nombreCompleto, correo, telefono, codigoPostal, contrasena);
        usuario.setRol("user");

        try {
            usuarioServicio.registrarUsuario(usuario);
            return "registroPendiente";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error: " + e.getMessage());
            return "registroFallido";
        }
    }

    @GetMapping("/confirmar")
    public String confirmarRegistro(@RequestParam String token) {
        boolean confirmado = usuarioServicio.confirmarRegistro(token);
        return confirmado ? "redirect:/usuarios/login" : "error";
    }
    
    @GetMapping("/registroPendiente")
    public ModelAndView registroPendienteForm() {
        ModelAndView mav = new ModelAndView("registroPendiente");
        return mav;
    }
}
