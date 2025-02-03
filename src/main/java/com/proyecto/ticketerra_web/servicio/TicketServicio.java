package com.proyecto.ticketerra_web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.proyecto.ticketerra_web.modelo.Ticket;

@Service
public class TicketServicio {

    private final String API_URL = "http://localhost:8081/api/tickets"; // URL de la API de tickets
    private final RestTemplate restTemplate;

    @Autowired
    public TicketServicio(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para crear un ticket
    public boolean crearTicket(Ticket ticket) {
        try {
            HttpEntity<Ticket> request = new HttpEntity<>(ticket);
            ResponseEntity<String> response = restTemplate.exchange(API_URL + "/crear", HttpMethod.POST, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
