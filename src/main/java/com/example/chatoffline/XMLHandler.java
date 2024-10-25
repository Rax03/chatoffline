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
    private static final String FILE_PATH = "usuarios.xml";

    public static void main(String[] args) {
        ensureFileExists();
        List<Usuario> usuarios = cargarUsuarios();
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Correo: " + usuario.getCorreo());
        }
    }

    private static void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                inicializarArchivo(); // Inicializa el archivo con una estructura básica
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void inicializarArchivo() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("usuarios");
            doc.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return usuarios;

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
            e.printStackTrace();
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
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void guardarMensajes(List<Mensaje> mensajes) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("chat");
            doc.appendChild(rootElement);

            Element mensajesElement = doc.createElement("mensajes");
            rootElement.appendChild(mensajesElement);

            for (Mensaje mensaje : mensajes) {
                Element mensajeElement = doc.createElement("mensaje");
                mensajeElement.setAttribute("remitente", mensaje.getRemitente());
                mensajeElement.setAttribute("destinatario", mensaje.getDestinatario());
                mensajeElement.setAttribute("fecha", mensaje.getFecha());
                mensajesElement.appendChild(mensajeElement);

                Element contenido = doc.createElement("texto");
                contenido.appendChild(doc.createTextNode(mensaje.getTexto()));
                mensajeElement.appendChild(contenido);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Mensaje> cargarMensajes() {
        List<Mensaje> mensajes = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return mensajes;

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
            e.printStackTrace();
        }
        return mensajes;
    }
}

