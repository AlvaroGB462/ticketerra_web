package com.proyecto.ticketerra_web.modelo;

public class Usuario {
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String codigoPostal;
    private String contrasena;
    private String repetirContrasena;

    private String tokenRecuperacion;
    private long tokenExpiracion;

    private boolean premium;
    private boolean activo;
    private String tokenConfirmacion;

    private String numeroTarjeta;
    private String nombreTarjeta;
    private String fechaExpiracion;
    private String cvv;

    private String rol;  // Campo para el rol

    // Constructor original
    public Usuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
        this.contrasena = contrasena;
        this.premium = false; // Por defecto, no es premium
        this.activo = false;  // Por defecto, no está activo
        this.rol = "user"; // Asignamos el rol por defecto
    }

    // Constructor para usuarios premium
    public Usuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena,
                   boolean premium, String numeroTarjeta, String nombreTarjeta, String fechaExpiracion, String cvv) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
        this.contrasena = contrasena;
        this.premium = premium;
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTarjeta = nombreTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
        this.activo = false;  // Por defecto, no está activo
        this.rol = "user"; // Por defecto, el rol es "user"
    }

    // Getters y Setters para los atributos existentes
    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public long getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(long tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRepetirContrasena() {
        return repetirContrasena;
    }

    public void setRepetirContrasena(String repetirContrasena) {
        this.repetirContrasena = repetirContrasena;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getTokenConfirmacion() {
        return tokenConfirmacion;
    }

    public void setTokenConfirmacion(String tokenConfirmacion) {
        this.tokenConfirmacion = tokenConfirmacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
