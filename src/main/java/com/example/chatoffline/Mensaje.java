package com.example.chatoffline;

import java.time.LocalDateTime;

public class Mensaje {
    private String de;
    private String para;
    private LocalDateTime fecha;
    private String contenido;

    public Mensaje(String de, String para, String contenido) {
        this.de = de;
        this.para = para;
        this.fecha = LocalDateTime.now();
        this.contenido = contenido;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}

