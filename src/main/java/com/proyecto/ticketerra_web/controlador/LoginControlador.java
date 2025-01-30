package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.ticketerra_web.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class LoginControlador {

    private UsuarioServicio usuarioServicio;

    @Autowired
    public LoginControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Mostrar la vista de login
    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("mensaje", "Por favor, ingrese sus credenciales");
        return mav;
    }

    // Manejar el login con datos del formulario
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, @RequestParam String contrasena, HttpSession session, Model model) {
        // Llamada al servicio para verificar las credenciales
        boolean loginExitoso = usuarioServicio.loginUsuario(correo, contrasena);

        if (loginExitoso) {
            session.setAttribute("usuario", correo); // Guardamos la sesión del usuario
            return "index"; // Redirige al index después de un login exitoso
        } else {
            model.addAttribute("mensaje", "Correo o contraseña incorrectos");
            return "login"; // Retorna a la vista de login con el mensaje de error
        }
    }

    // Mostrar formulario para ingresar el correo
    @GetMapping("/recuperar")
    public ModelAndView mostrarRecuperarContrasena() {
        ModelAndView mav = new ModelAndView("recuperar");
        return mav;
    }

    // Procesar la solicitud de recuperación
    @PostMapping("/recuperar")
    public String procesarRecuperacion(@RequestParam String correo, Model model) {
        boolean enviado = usuarioServicio.enviarTokenRecuperacion(correo);

        if (enviado) {
            model.addAttribute("mensaje", "Revisa tu correo para cambiar la contraseña.");
        } else {
            model.addAttribute("mensaje", "El correo no está registrado.");
        }

        return "recuperar";
    }

    // Mostrar formulario para restablecer la contraseña
    @GetMapping("/restablecer")
    public ModelAndView mostrarFormularioRestablecer(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        ModelAndView mav = new ModelAndView("restablecer");
        return mav;
    }

    // Procesar cambio de contraseña
    @PostMapping("/restablecer")
    public String procesarRestablecer(@RequestParam String token, @RequestParam String nuevaContrasena,
                                      @RequestParam String repetirContrasena, Model model) {

        if (!nuevaContrasena.equals(repetirContrasena)) {
            model.addAttribute("mensaje", "Las contraseñas no coinciden.");
            return "restablecer";
        }

        boolean cambiado = usuarioServicio.restablecerContrasena(token, nuevaContrasena);

        if (cambiado) {
            model.addAttribute("mensaje", "Contraseña cambiada con éxito. Inicia sesión.");
            return "login"; // Redirigir al login
        } else {
            model.addAttribute("mensaje", "Error al cambiar la contraseña.");
            return "restablecer";
        }
    }
}
