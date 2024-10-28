package com.example.chatoffline;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHandler {
    private static final String FILE_PATH_USUARIOS = "usuarios.xml";
    private static final String FILE_PATH_MENSAJES = "mensajes.xml";

    public static void ensureFileExists(String filePath, String rootElementName) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    inicializarArchivo(filePath, rootElementName);
                } else {
                    System.out.println("No se pudo crear el archivo: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e.getMessage());
            }
        }
    }

    private static void inicializarArchivo(String filePath, String rootElementName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement(rootElementName);
            doc.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println("Error al inicializar el archivo: " + e.getMessage());
        }
    }

    public static List<Usuario> cargarUsuarios() {
        ensureFileExists(FILE_PATH_USUARIOS, "usuarios");
        List<Usuario> usuarios = new ArrayList<>();
        try {
            File file = new File(FILE_PATH_USUARIOS);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("usuario");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                    String correo = eElement.getElementsByTagName("correo").item(0).getTextContent();
                    usuarios.add(new Usuario(id, nombre, correo));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public static void guardarUsuarios(List<Usuario> usuarios) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("usuarios");
            doc.appendChild(rootElement);

            for (Usuario usuario : usuarios) {
                Element usuarioElement = doc.createElement("usuario");
                usuarioElement.setAttribute("id", usuario.getId());
                rootElement.appendChild(usuarioElement);

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(usuario.getNombre()));
                usuarioElement.appendChild(nombre);

                Element correo = doc.createElement("correo");
                correo.appendChild(doc.createTextNode(usuario.getCorreo()));
                usuarioElement.appendChild(correo);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH_USUARIOS));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println("Error al guardar los usuarios: " + e.getMessage());
        }
    }

    public static List<Mensaje> cargarMensajes() {
        ensureFileExists(FILE_PATH_MENSAJES, "mensajes");
        List<Mensaje> mensajes = new ArrayList<>();
        try {
            File file = new File(FILE_PATH_MENSAJES);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("mensaje");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String remitente = eElement.getAttribute("remitente");
                    String destinatario = eElement.getAttribute("destinatario");
                    String fecha = eElement.getAttribute("fecha");
                    String texto = eElement.getElementsByTagName("texto").item(0).getTextContent();
                    mensajes.add(new Mensaje(remitente, destinatario, texto, fecha));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los mensajes: " + e.getMessage());
        }
        return mensajes;
    }

    public static void guardarMensajes(List<Mensaje> mensajes) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("mensajes");
            doc.appendChild(rootElement);

            for (Mensaje mensaje : mensajes) {
                Element mensajeElement = doc.createElement("mensaje");
                mensajeElement.setAttribute("remitente", mensaje.getRemitente());
                mensajeElement.setAttribute("destinatario", mensaje.getDestinatario());
                mensajeElement.setAttribute("fecha", mensaje.getFecha());
                rootElement.appendChild(mensajeElement);

                Element contenido = doc.createElement("texto");
                contenido.appendChild(doc.createTextNode(mensaje.getTexto()));
                mensajeElement.appendChild(contenido);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH_MENSAJES));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println("Error al guardar los mensajes: " + e.getMessage());
        }
    }
}
