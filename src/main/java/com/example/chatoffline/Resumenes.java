package com.example.chatoffline;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Resumenes {

    public static void main(String[] args) {
        Resumenes resumenes = new Resumenes();
        resumenes.generarResumenPorRemitente("1");
    }

    // Método para generar un resumen de mensajes enviados por un remitente específico
    public void generarResumenPorRemitente(String remitenteId) {
        List<Mensaje> mensajes = obtenerMensajes();
        List<Mensaje> mensajesFiltrados = mensajes.stream()
                .filter(mensaje -> mensaje.getRemitente().equals(remitenteId))
                .collect(Collectors.toList());

        System.out.println("Mensajes enviados por el remitente " + remitenteId + ":");
        mensajesFiltrados.forEach(mensaje -> {
            System.out.println("Para: " + mensaje.getDestinatario() + ", Texto: " + mensaje.getTexto() + ", Fecha: " + mensaje.getFecha());
        });
    }

    // Método para obtener los mensajes del archivo XML
    private List<Mensaje> obtenerMensajes() {
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
}
