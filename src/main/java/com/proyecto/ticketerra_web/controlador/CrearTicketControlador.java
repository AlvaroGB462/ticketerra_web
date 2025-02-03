package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.ticketerra_web.modelo.Ticket;
import com.proyecto.ticketerra_web.servicio.TicketServicio;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/ticket")
public class CrearTicketControlador {

    private final TicketServicio ticketServicio;

    @Autowired
    public CrearTicketControlador(TicketServicio ticketServicio) {
        this.ticketServicio = ticketServicio;
    }

    // Mostrar la vista de crearTicket
    @GetMapping("/crearTicket")
    public ModelAndView mostrarFormulario() {
        return new ModelAndView("crearTicket");
    }

    // Procesar el formulario de creación de ticket
    @PostMapping("/crearTicket")
    public String crearTicket(@RequestParam String categoria, 
                              @RequestParam String asunto, 
                              @RequestParam String descripcion, 
                              @RequestParam MultipartFile archivo, 
                              @RequestParam String correoUsuario, 
                              Model model) {
        try {
            // Convertir archivo a Base64 (para simplificar el envío)
            String archivoBase64 = archivo.isEmpty() ? null : Base64.getEncoder().encodeToString(archivo.getBytes());

            // Crear el objeto Ticket
            Ticket ticket = new Ticket(categoria, asunto, descripcion, archivoBase64, correoUsuario);

            // Llamar al servicio para guardar el ticket
            boolean creado = ticketServicio.crearTicket(ticket);

            if (creado) {
                return "redirect:/ticket/misTickets"; // Redirigir a la vista de tickets del usuario
            } else {
                model.addAttribute("error", "Hubo un problema al crear el ticket.");
                return "crearTicket";
            }

        } catch (IOException e) {
            model.addAttribute("error", "Error al procesar el archivo.");
            return "crearTicket";
        }
    }
}
