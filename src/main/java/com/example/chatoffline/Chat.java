package com.example.chatoffline;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Mensaje> mensajes = new ArrayList<>();

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void enviarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        XMLHandler.guardarMensajes(mensajes); // Suponiendo que tienes una clase XMLHandler para manejar la escritura en el XML
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
}
