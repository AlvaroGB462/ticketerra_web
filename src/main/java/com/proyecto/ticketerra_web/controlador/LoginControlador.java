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
	public String procesarLogin(@RequestParam String correo, @RequestParam String contrasena, HttpSession session,
	        Model model) {
	    // Llamada al servicio para verificar las credenciales
	    boolean loginExitoso = usuarioServicio.loginUsuario(correo, contrasena);

	    if (loginExitoso) {
	        session.setAttribute("usuario", correo);
	        return "redirect:/dashboard"; // Redirigir a una página de dashboard o inicio
	    } else {
	        model.addAttribute("mensaje", "Correo o contraseña incorrectos");
	        return "login"; // Retorna a la vista de login con un mensaje de error
	    }
	}

	// Mostrar la vista de recuperar
	@GetMapping("/recuperar")
	public ModelAndView recuperarContrasenaForm() {
		ModelAndView mav = new ModelAndView("recuperar");
		mav.addObject("mensaje", "Introduce tu correo para recuperar tu contraseña");
		return mav;
	}

	@PostMapping("/recuperar")
	public String procesarRecuperacion(@RequestParam String correo, Model model) {
	    // Llamada al servicio para generar el token
	    boolean correoEnviado = usuarioServicio.enviarTokenRecuperacion(correo);

	    if (correoEnviado) {
	        model.addAttribute("mensaje", "Se ha enviado un enlace de recuperación a tu correo.");
	        return "recuperar"; // Retorna a la vista con mensaje
	    } else {
	        model.addAttribute("mensaje", "El correo no está registrado.");
	        return "recuperar"; // Retorna a la vista con mensaje de error
	    }
	}
}
