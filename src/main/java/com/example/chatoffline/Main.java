package com.example.chatoffline;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                    registrarUsuario(scanner);
                    break;
                case 2:
                    enviarMensaje(scanner);
                    break;
                case 3:
                    verMensajes();
                    break;
                case 4:
                    generarInforme(scanner);
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

    public static void registrarUsuario(Scanner scanner) {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        String fechaRegistro = java.time.LocalDate.now().toString();

        Usuario usuario = new Usuario(id, nombre, correo);
        agregarUsuario(usuario);
    }

    public static void agregarUsuario(Usuario usuario) {
        try {
            File inputFile = new File("usuarios.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            Node root = doc.getFirstChild();

            Element nuevoUsuario = doc.createElement("usuario");
            nuevoUsuario.setAttribute("id", usuario.getId());

            Element nombre = doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode(usuario.getNombre()));
            nuevoUsuario.appendChild(nombre);

            Element correo = doc.createElement("correo");
            correo.appendChild(doc.createTextNode(usuario.getCorreo()));
            nuevoUsuario.appendChild(correo);

            Element fechaRegistro = doc.createElement("fechaRegistro");
            fechaRegistro.appendChild(doc.createTextNode(usuario.getId()));
            nuevoUsuario.appendChild(fechaRegistro);

            root.appendChild(nuevoUsuario);

            // Guardar los cambios en el archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("usuarios.xml"));
            transformer.transform(source, result);

            System.out.println("Usuario agregado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enviarMensaje(Scanner scanner) {
        System.out.print("Remitente (ID): ");
        String remitente = scanner.nextLine();
        System.out.print("Destinatario (ID): ");
        String destinatario = scanner.nextLine();
        System.out.print("Texto: ");
        String texto = scanner.nextLine();
        String fecha = java.time.LocalDateTime.now().toString();

        Mensaje mensaje = new Mensaje(remitente, destinatario, texto, fecha);
        agregarMensaje(mensaje);
    }

    public static void agregarMensaje(Mensaje mensaje) {
        try {
            File inputFile = new File("mensajes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            Node root = doc.getFirstChild();

            Element nuevoMensaje = doc.createElement("mensaje");

            Element remitente = doc.createElement("remitente");
            remitente.appendChild(doc.createTextNode(mensaje.getRemitente()));
            nuevoMensaje.appendChild(remitente);

            Element destinatario = doc.createElement("destinatario");
            destinatario.appendChild(doc.createTextNode(mensaje.getDestinatario()));
            nuevoMensaje.appendChild(destinatario);

            Element texto = doc.createElement("texto");
            texto.appendChild(doc.createTextNode(mensaje.getTexto()));
            nuevoMensaje.appendChild(texto);

            Element fecha = doc.createElement("fecha");
            fecha.appendChild(doc.createTextNode(mensaje.getFecha()));
            nuevoMensaje.appendChild(fecha);

            root.appendChild(nuevoMensaje);

            // Guardar los cambios en el archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("mensajes.xml"));
            transformer.transform(source, result);

            System.out.println("Mensaje agregado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verMensajes() {
        List<Mensaje> mensajes = obtenerMensajes();
        mensajes.forEach(mensaje -> {
            System.out.println("De: " + mensaje.getRemitente() + " Para: " + mensaje.getDestinatario());
            System.out.println("Mensaje: " + mensaje.getTexto());
            System.out.println("Fecha: " + mensaje.getFecha());
            System.out.println("----------");
        });
    }

    public static List<Mensaje> obtenerMensajes() {
        List<Mensaje> mensajes = new ArrayList<>();
        try {
            File inputFile = new File("mensajes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("mensaje");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String remitente = eElement.getElementsByTagName("remitente").item(0).getTextContent();
                    String destinatario = eElement.getElementsByTagName("destinatario").item(0).getTextContent();
                    String texto = eElement.getElementsByTagName("texto").item(0).getTextContent();
                    String fecha = eElement.getElementsByTagName("fecha").item(0).getTextContent();

                    Mensaje mensaje = new Mensaje(remitente, destinatario, texto, fecha);
                    mensajes.add(mensaje);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public static void generarInforme(Scanner scanner) {
        System.out.print("ID del remitente: ");
        String remitenteId = scanner.nextLine();
        generarInforme(remitenteId);
    }

    public static void generarInforme(String remitenteId) {
        List<Mensaje> mensajes = obtenerMensajes();
        mensajes.stream()
                .filter(mensaje -> mensaje.getRemitente().equals(remitenteId))
                .forEach(mensaje -> System.out.println("Para: " + mensaje.getDestinatario() + ", Texto: " + mensaje.getTexto() + ", Fecha: " + mensaje.getFecha()));
    }
}
