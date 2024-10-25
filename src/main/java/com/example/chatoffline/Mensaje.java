package com.example.chatoffline;

public class Mensaje {
    private String remitente;
    private String destinatario;
    private String texto;
    private String fecha;

    // Constructor
    public Mensaje(String remitente, String destinatario, String texto, String fecha) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.fecha = fecha;
    }

    // Getters
    public String getRemitente() {
        return remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public String getFecha() {
        return fecha;
    }

    // Setters
    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // toString method for easy display
    @Override
    public String toString() {
        return "Mensaje{" +
                "remitente='" + remitente + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", texto='" + texto + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
