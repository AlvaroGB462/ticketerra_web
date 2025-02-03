package com.proyecto.ticketerra_web.modelo;

public class Ticket {
    private Long id;
    private String categoria;
    private String asunto;
    private String descripcion;
    private String archivo;
    private String correoUsuario; // Para vincular el ticket con un usuario

    // Constructores
    public Ticket() {
    }

    public Ticket(String categoria, String asunto, String descripcion, String archivo, String correoUsuario) {
        this.categoria = categoria;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.correoUsuario = correoUsuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
}
