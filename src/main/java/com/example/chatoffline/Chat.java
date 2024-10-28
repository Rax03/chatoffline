package com.example.chatoffline;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Mensaje> mensajes = new ArrayList<>();

    public Chat() {
        try {
            usuarios = XMLHandler.cargarUsuarios();
            mensajes = XMLHandler.cargarMensajes();
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public void registrarUsuario(Usuario usuario) {
        try {
            usuarios.add(usuario);
            XMLHandler.guardarUsuarios(usuarios);
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void enviarMensaje(Mensaje mensaje) {
        try {
            mensajes.add(mensaje);
            XMLHandler.guardarMensajes(mensajes);
        } catch (Exception e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
        }
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public String generarResumen() {
        StringBuilder resumen = new StringBuilder();
        for (Mensaje mensaje : mensajes) {
            resumen.append(String.format("De: %s, Para: %s, Fecha: %s, Mensaje: %s\n",
                    mensaje.getRemitente(), mensaje.getDestinatario(), mensaje.getFecha(), mensaje.getTexto()));
        }
        return resumen.toString();
    }

    public void guardarResumenEnArchivo(String remitenteId, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Mensaje mensaje : mensajes) {
                if (mensaje.getRemitente().equals(remitenteId)) {
                    writer.write(String.format("Para: %s, Fecha: %s, Mensaje: %s\n",
                            mensaje.getDestinatario(), mensaje.getFecha(), mensaje.getTexto()));
                }
            }
            System.out.println("Resumen guardado en " + filePath);
        } catch (IOException e) {
            System.err.println("Error al guardar resumen en archivo: " + e.getMessage());
        }
    }
}
