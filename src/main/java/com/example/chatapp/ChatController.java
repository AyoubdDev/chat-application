package com.example.chatapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private ChatClient client;

    public void initialize() {
        client = new ChatClient("localhost", 1234, message -> {
            chatArea.appendText(message + "\n");
        });
    }

    @FXML
    protected void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            client.sendMessage("Client: " + message);
            messageField.clear();
        }
    }
}
