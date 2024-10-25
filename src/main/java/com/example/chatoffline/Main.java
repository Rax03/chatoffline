package com.example.chatoffline;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Chat chat = new Chat();

        while (true) {
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Enviar Mensaje");
            System.out.println("3. Ver Mensajes");
            System.out.println("4. Generar Informe");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            switch (opcion) {
                case 1:
                    registrarUsuario(chat, scanner);
                    break;
                case 2:
                    enviarMensaje(chat, scanner);
                    break;
                case 3:
                    verMensajes(chat);
                    break;
                case 4:
                    generarInforme(chat, scanner);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public static void registrarUsuario(Chat chat, Scanner scanner) {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        Usuario usuario = new Usuario(id, nombre, correo);
        chat.registrarUsuario(usuario);
        System.out.println("Usuario registrado exitosamente.");
    }

    public static void enviarMensaje(Chat chat, Scanner scanner) {
        System.out.print("Remitente (ID): ");
        String remitente = scanner.nextLine();
        System.out.print("Destinatario (ID): ");
        String destinatario = scanner.nextLine();
        System.out.print("Texto: ");
        String texto = scanner.nextLine();
        String fecha = java.time.LocalDateTime.now().toString();
        Mensaje mensaje = new Mensaje(remitente, destinatario, texto, fecha);
        chat.enviarMensaje(mensaje);
        System.out.println("Mensaje enviado exitosamente.");
    }

    public static void verMensajes(Chat chat) {
        List<Mensaje> mensajes = chat.getMensajes();
        for (Mensaje mensaje : mensajes) {
            System.out.println("De: " + mensaje.getRemitente() + " Para: " + mensaje.getDestinatario());
            System.out.println("Mensaje: " + mensaje.getTexto());
            System.out.println("Fecha: " + mensaje.getFecha());
            System.out.println("----------");
        }
    }

    public static void generarInforme(Chat chat, Scanner scanner) {
        System.out.print("ID del remitente: ");
        String remitenteId = scanner.nextLine();
        List<Mensaje> mensajes = chat.getMensajes();
        StringBuilder resumen = new StringBuilder();
        for (Mensaje mensaje : mensajes) {
            if (mensaje.getRemitente().equals(remitenteId)) {
                resumen.append(String.format("Para: %s, Fecha: %s, Mensaje: %s\n",
                        mensaje.getDestinatario(), mensaje.getFecha(), mensaje.getTexto()));
            }
        }
        System.out.println("Resumen de mensajes del remitente " + remitenteId + ":");
        System.out.println(resumen.toString());
    }
}
