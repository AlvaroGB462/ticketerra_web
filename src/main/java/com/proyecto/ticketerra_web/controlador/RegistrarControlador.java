package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "registrar"; // Devuelve la vista del formulario de registro
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String nombreCompleto, 
                                   @RequestParam String correo,
                                   @RequestParam String telefono, 
                                   @RequestParam String codigoPostal, 
                                   @RequestParam String contrasena, 
                                   @RequestParam String repetirContrasena, 
                                   @RequestParam(required = false) Boolean premium,
                                   @RequestParam(required = false) String numeroTarjeta, 
                                   @RequestParam(required = false) String nombreTarjeta, 
                                   @RequestParam(required = false) String fechaExpiracion, 
                                   @RequestParam(required = false) String cvv,
                                   Model model) {

        // Validación de contraseñas
        if (!contrasena.equals(repetirContrasena)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "registrar"; // Retorna al formulario
        }

        // Crear el usuario
        Usuario usuario = new Usuario(nombreCompleto, correo, telefono, codigoPostal, contrasena);

        // Asignar rol por defecto a "user"
        usuario.setRol("user");

        // Validación para usuarios premium
        if (premium != null && premium) {
            if (numeroTarjeta == null || nombreTarjeta == null || fechaExpiracion == null || cvv == null) {
                model.addAttribute("error", "Por favor, complete todos los campos de la tarjeta para el registro premium.");
                return "registrar"; // Retorna al formulario
            }

            // Establecer la información premium
            usuario.setPremium(true);
            usuario.setNumeroTarjeta(numeroTarjeta);
            usuario.setNombreTarjeta(nombreTarjeta);
            usuario.setFechaExpiracion(fechaExpiracion);
            usuario.setCvv(cvv);
        }

        // Aquí también se puede almacenar la fecha de registro si es necesario
        // usuario.setFechaRegistro(new Date()); // O cualquier lógica de fecha que necesites

        try {
            // Registrar usuario
            usuarioServicio.registrarUsuario(usuario);
            usuarioServicio.enviarCorreoConfirmacion(correo);

            return "redirect:/usuarios/registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error: " + e.getMessage());
            return "registroFallido";
        }
    }
}
