package com.example.chatoffline;

import java.util.List;

public class Resumenes {

    public static String generarResumenPorUsuario(Chat chat, String usuarioId) {
        StringBuilder resumen = new StringBuilder();
        List<Mensaje> mensajes = chat.getMensajes();
        for (Mensaje mensaje : mensajes) {
            if (mensaje.getDe().equals(usuarioId) || mensaje.getPara().equals(usuarioId)) {
                resumen.append(String.format("De: %s, Para: %s, Fecha: %s, Mensaje: %s\n",
                        mensaje.getDe(), mensaje.getPara(), mensaje.getFecha().toString(), mensaje.getContenido()));
            }
        }
        return resumen.toString();
    }

    public static String generarResumenCompleto(Chat chat) {
        StringBuilder resumen = new StringBuilder();
        List<Mensaje> mensajes = chat.getMensajes();
        for (Mensaje mensaje : mensajes) {
            resumen.append(String.format("De: %s, Para: %s, Fecha: %s, Mensaje: %s\n",
                    mensaje.getDe(), mensaje.getPara(), mensaje.getFecha().toString(), mensaje.getContenido()));
        }
        return resumen.toString();
    }
}

