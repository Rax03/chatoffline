package com.example.chatoffline;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLHandler {
    private static final String FILE_PATH = "chat.xml";

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
                mensajeElement.setAttribute("de", mensaje.getDe());
                mensajeElement.setAttribute("para", mensaje.getPara());
                mensajeElement.setAttribute("fecha", mensaje.getFecha().toString());
                mensajesElement.appendChild(mensajeElement);

                Element contenido = doc.createElement("contenido");
                contenido.appendChild(doc.createTextNode(mensaje.getContenido()));
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

                Element email = doc.createElement("email");
                email.appendChild(doc.createTextNode(usuario.getEmail()));
                usuarioElement.appendChild(email);
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
                    String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    usuarios.add(new Usuario(id, nombre, email));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
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
                    String de = eElement.getAttribute("de");
                    String para = eElement.getAttribute("para");
                    String fecha = eElement.getAttribute("fecha");
                    String contenido = eElement.getElementsByTagName("contenido").item(0).getTextContent();
                    mensajes.add(new Mensaje(de, para, contenido));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }
}
