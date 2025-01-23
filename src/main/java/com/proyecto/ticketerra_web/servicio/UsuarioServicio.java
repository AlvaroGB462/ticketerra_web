package com.proyecto.ticketerra_web.servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.proyecto.ticketerra_web.modelo.Usuario;

@Service
public class UsuarioServicio {

    private final String API_URL = "http://localhost:8080/api/usuarios";  
    private final RestTemplate restTemplate;

    public UsuarioServicio(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = new Usuario(null, correo, null, null, contrasena);
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "/login", usuario, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public void registrarUsuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena) {
        Usuario usuario = new Usuario(nombreCompleto, correo, telefono, codigoPostal, contrasena);
        restTemplate.postForEntity(API_URL + "/registrar", usuario, String.class);
    }
}
