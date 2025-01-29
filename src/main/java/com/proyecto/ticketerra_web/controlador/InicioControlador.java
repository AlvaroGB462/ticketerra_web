package com.proyecto.ticketerra_web.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class InicioControlador {

    // Mostrar la vista de inicio cuando se accede a /usuarios/index
    @GetMapping("/index")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    // Redirigir desde la raíz (/) a /usuarios/index
    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/usuarios/index"; // Redirige a /usuarios/index
    }
}
