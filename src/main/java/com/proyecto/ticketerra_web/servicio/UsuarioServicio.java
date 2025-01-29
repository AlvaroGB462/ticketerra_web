package com.proyecto.ticketerra_web.servicio;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.proyecto.ticketerra_web.modelo.Usuario;

@Service
public class UsuarioServicio {

    private final String API_URL = "http://localhost:8080/api/usuarios"; // URL de la API
    private final RestTemplate restTemplate;
    private final JavaMailSender mailSender;

    @Autowired
    public UsuarioServicio(RestTemplate restTemplate, JavaMailSender mailSender) {
        this.restTemplate = restTemplate;
        this.mailSender = mailSender;
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = new Usuario(null, correo, null, null, contrasena);
        
        // Convertir el usuario a JSON (automaticamente hecho por RestTemplate)
        HttpEntity<Usuario> request = new HttpEntity<>(usuario);
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "/login", request, String.class);

        // Verificar si la respuesta de la API fue exitosa
        return response.getStatusCode().is2xxSuccessful();
    }

    public void registrarUsuario(Usuario usuario) {
        // Convertir el usuario a JSON (RestTemplate lo convierte automáticamente)
        HttpEntity<Usuario> request = new HttpEntity<>(usuario);
        
        // Enviar el usuario como JSON al API para registrarse
        restTemplate.postForEntity(API_URL + "/registrar", request, String.class);
    }
    
    public boolean enviarTokenRecuperacion(String correo) {
        // Buscar usuario por correo
        Usuario usuario = restTemplate.getForObject(API_URL + "/buscarPorCorreo?correo=" + correo, Usuario.class);
        if (usuario == null) {
            return false; // El correo no está registrado
        }

        String token = UUID.randomUUID().toString(); // Generar token único
        usuario.setTokenRecuperacion(token);

        // Actualizar el token en la base de datos
        HttpEntity<Usuario> request = new HttpEntity<>(usuario);
        restTemplate.postForEntity(API_URL + "/actualizarToken", request, String.class);

        // Enviar el correo con el token
        return enviarCorreoRecuperacion(correo, token);
    }

    private boolean enviarCorreoRecuperacion(String correo, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Recuperación de contraseña - Ticketerra");
            message.setText("Haz clic en el siguiente enlace para recuperar tu contraseña: \n"
                    + "http://localhost:8080/usuarios/restablecer?token=" + token);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enviarCorreoConfirmacion(String correo) throws Exception {
        try {
            String tokenConfirmacion = UUID.randomUUID().toString();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Confirmación de Registro - Ticketerra");
            message.setText("Haz clic en el siguiente enlace para confirmar tu registro: \n"
                    + "http://localhost:8080/usuarios/confirmar?token=" + tokenConfirmacion);
            mailSender.send(message);
        } catch (Exception e) {
            throw new Exception("Error al enviar correo de confirmación: " + e.getMessage());
        }
    }

    public boolean confirmarRegistro(String token) {
        // Aquí se hace una llamada a la API REST para obtener el usuario por su token
        String apiUrl = API_URL + "/buscarPorTokenConfirmacion?token=" + token;  // Asegúrate de tener este endpoint en tu API
        Usuario usuario = restTemplate.getForObject(apiUrl, Usuario.class);

        if (usuario != null) {
            // Si el usuario existe, activamos el registro
            usuario.setActivo(true);
            usuario.setTokenConfirmacion(null); // Limpiar el token

            // Actualizamos el usuario con la API
            HttpEntity<Usuario> request = new HttpEntity<>(usuario);
            restTemplate.postForEntity(API_URL + "/actualizarUsuario", request, String.class);

            return true;
        }
        return false;
    }
    
    public boolean procesarPago(String numeroTarjeta, String nombreTarjeta, String fechaExpiracion, String cvv) {
        // Integración con sistema de pagos (Ej. Stripe, PayPal, etc.)
        // Aquí se debe realizar la integración con una API externa de pago.
        // Este es solo un ejemplo de cómo podrías iniciar el proceso.

        boolean pagoExitoso = false;
        try {
            // Realizar llamada a la API del sistema de pagos
            // Si el pago es exitoso, cambiar el estado del usuario a premium
            pagoExitoso = true; // Suponiendo que el pago fue exitoso
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pagoExitoso;
    }
}
