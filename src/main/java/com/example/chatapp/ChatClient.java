package com.example.chatapp;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;

    public ChatClient(String host, int port, Consumer<String> onMessageReceived) {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);

            // Thread qui écoute les messages du serveur
            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String message;
                    while ((message = in.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> onMessageReceived.accept(finalMessage));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
}
