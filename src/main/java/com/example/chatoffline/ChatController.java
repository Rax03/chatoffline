package com.example.chatoffline;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userEmailField;
    @FXML
    private TextField loginNameField;

    private Chat chat;
    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        chat = new Chat();
        chat.getUsuarios().addAll(XMLHandler.cargarUsuarios());
        chat.getMensajes().addAll(XMLHandler.cargarMensajes());
        cargarMensajesEnChatArea();
    }

    private void cargarMensajesEnChatArea() {
        chatArea.clear();
        for (Mensaje mensaje : chat.getMensajes()) {
            chatArea.appendText(String.format("De: %s, Para: %s, Fecha: %s, Mensaje: %s\n",
                    mensaje.getDe(), mensaje.getPara(), mensaje.getFecha().toString(), mensaje.getContenido()));
        }
    }

    @FXML
    private void sendMessage() {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Debes iniciar sesión antes de enviar mensajes.");
            return;
        }

        String message = inputField.getText();
        if (!message.isEmpty()) {
            Mensaje nuevoMensaje = new Mensaje(usuarioActual.getNombre(), "Usuario2", message);
            chat.enviarMensaje(nuevoMensaje);
            chatArea.appendText(String.format("Yo: %s\n", message));
            inputField.clear();
        }
    }

    @FXML
    private void registerUser() {
        String nombre = userNameField.getText();
        String email = userEmailField.getText();
        if (!nombre.isEmpty() && !email.isEmpty()) {
            Usuario nuevoUsuario = new Usuario(String.valueOf(chat.getUsuarios().size() + 1), nombre, email);
            chat.registrarUsuario(nuevoUsuario);
            userNameField.clear();
            userEmailField.clear();
            System.out.println("Usuario registrado: " + nuevoUsuario);
        }
    }

    @FXML
    private void loginUser() {
        String nombre = loginNameField.getText();
        for (Usuario usuario : chat.getUsuarios()) {
            if (usuario.getNombre().equals(nombre)) {
                usuarioActual = usuario;
                mostrarAlerta("Inicio de sesión exitoso", "Bienvenido, " + usuarioActual.getNombre());
                return;
            }
        }
        mostrarAlerta("Error", "Usuario no encontrado.");
    }

    @FXML
    private void cargarResumen() {
        String resumen = chat.generarResumen();
        System.out.println("Resumen de la conversación:\n" + resumen);
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
