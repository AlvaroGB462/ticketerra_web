package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.ticketerra_web.modelo.Usuario;
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
 // Mostrar la vista de login
    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("mensaje", "Por favor, ingrese sus credenciales");
        return mav;
    }

    // Manejar el login con datos del formulario
    @PostMapping("/login")
    public ModelAndView procesarLogin(@RequestParam String correo, @RequestParam String contrasena,
                                      HttpSession session) {

        boolean loginExitoso = usuarioServicio.loginUsuario(correo, contrasena);

        if (loginExitoso) {
            // Obtenemos el objeto Usuario completo
            Usuario usuario = usuarioServicio.obtenerUsuarioPorCorreo(correo);
            
            // Guardamos el objeto completo Usuario en la sesión
            session.setAttribute("usuario", usuario); 
            session.setMaxInactiveInterval(1200);
            
            // Redirigimos a la página principal
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("usuario", usuario);
            return mav;
        } else {
            // Si el login falla, volvemos al login con un mensaje de error
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("mensaje", "Correo o contraseña incorrectos");
            return mav;
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
    public ResponseEntity<String> procesarRecuperacion(@RequestParam String correo) {
        boolean enviado = usuarioServicio.enviarTokenRecuperacion(correo);

        if (enviado) {
            return ResponseEntity.ok("Revisa tu correo para cambiar la contraseña.");
        } else {
            return ResponseEntity.status(400).body("El correo no está registrado.");
        }
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
    public ResponseEntity<String> procesarRestablecer(@RequestParam String token, @RequestParam String nuevaContrasena,
            @RequestParam String repetirContrasena, Model model) {

        if (!nuevaContrasena.equals(repetirContrasena)) {
            return ResponseEntity.badRequest().body("Las contraseñas no coinciden.");
        }

        boolean cambiado = usuarioServicio.restablecerContrasena(token, nuevaContrasena);

        if (cambiado) {
            return ResponseEntity.ok("Contraseña cambiada con éxito. Inicia sesión.");
        } else {
            return ResponseEntity.status(500).body("Error al cambiar la contraseña.");
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalida la sesión
        return "redirect:/usuarios/login"; // Redirige al login después de cerrar sesión
    }
}
