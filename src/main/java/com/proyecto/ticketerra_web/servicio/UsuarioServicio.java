package com.proyecto.ticketerra_web.servicio;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.proyecto.ticketerra_web.modelo.Usuario;

@Service
public class UsuarioServicio {

    private final String API_URL = "http://localhost:8081/api/usuarios"; // URL de la API
    private final RestTemplate restTemplate;
    private final JavaMailSender mailSender;

    @Autowired
    public UsuarioServicio(RestTemplate restTemplate, JavaMailSender mailSender) {
        this.restTemplate = restTemplate;
        this.mailSender = mailSender;
    }

    // Método para obtener el objeto Usuario por correo
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String url = API_URL + "/usuario?correo=" + correo;
        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.GET, null, Usuario.class);
        return response.getBody();
    }

    public void registrarUsuario(Usuario usuario) {
        restTemplate.postForEntity(API_URL + "/registrar", usuario, String.class);
    }

    public boolean confirmarRegistro(String token) {
        String url = API_URL + "/confirmar?token=" + token;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean loginUsuario(String correo, String contrasena) {
        String url = API_URL + "/login";
        Usuario usuario = new Usuario(correo, contrasena); // Usamos el constructor correcto

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(usuario), String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enviarTokenRecuperacion(String correo) {
        String url = API_URL + "/recuperar";
        HttpEntity<String> request = new HttpEntity<>(correo);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean restablecerContrasena(String token, String nuevaContrasena) {
        String url = API_URL + "/restablecer?token=" + token + "&nuevaContrasena=" + nuevaContrasena;
        try {
            restTemplate.postForEntity(url, null, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean enviarCorreoRecuperacion(String correo, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Recuperación de contraseña");
            message.setText("Haz clic aquí para cambiar tu contraseña:\n"
                    + "http://localhost:9094/usuarios/restablecer?token=" + token);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
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
                    + "http://localhost:9094/usuarios/confirmar?token=" + tokenConfirmacion);
            mailSender.send(message);
        } catch (Exception e) {
            throw new Exception("Error al enviar correo de confirmación: " + e.getMessage());
        }
    }
}
