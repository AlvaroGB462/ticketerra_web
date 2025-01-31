package com.proyecto.ticketerra_web.modelo;

public class ErrorResponse {

    private String message;
    private int code;

    // Constructor
    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    // Getter para el mensaje
    public String getMessage() {
        return message;
    }

    // Getter para el código de error
    public int getCode() {
        return code;
    }

    // Setter para el mensaje
    public void setMessage(String message) {
        this.message = message;
    }

    // Setter para el código de error
    public void setCode(int code) {
        this.code = code;
    }
}
