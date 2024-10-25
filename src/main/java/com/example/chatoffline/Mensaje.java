package com.example.chatoffline;

public class Mensaje {
    private String remitente;
    private String destinatario;
    private String texto;
    private String fecha;

    public Mensaje(String remitente, String destinatario, String texto, String fecha) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.fecha = fecha;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}