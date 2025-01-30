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

	private final String API_URL = "http://localhost:8081/api/usuarios"; // URL de la API
	private final RestTemplate restTemplate;
	private final JavaMailSender mailSender;

	@Autowired
	public UsuarioServicio(RestTemplate restTemplate, JavaMailSender mailSender) {
		this.restTemplate = restTemplate;
		this.mailSender = mailSender;
	}

	public void registrarUsuario(Usuario usuario) {
		// Convertir el usuario a JSON (RestTemplate lo convierte automáticamente)
		HttpEntity<Usuario> request = new HttpEntity<>(usuario);

		// Enviar el usuario como JSON al API para registrarse
		restTemplate.postForEntity(API_URL + "/registrar", request, String.class);
	}

	public boolean loginUsuario(String correo, String contrasena) {
		String url = API_URL + "/login?correo=" + correo + "&contrasena=" + contrasena;
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			return response.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean enviarTokenRecuperacion(String correo) {
		String url = API_URL + "/recuperar?correo=" + correo;
		ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
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
					+ "http://localhost:8080/usuarios/restablecer?token=" + token);
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
					+ "http://localhost:8080/usuarios/confirmar?token=" + tokenConfirmacion);
			mailSender.send(message);
		} catch (Exception e) {
			throw new Exception("Error al enviar correo de confirmación: " + e.getMessage());
		}
	}

	public boolean confirmarRegistro(String token) {
		String url = API_URL + "/confirmar?token=" + token;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		return response.getStatusCode().is2xxSuccessful();
	}
}
