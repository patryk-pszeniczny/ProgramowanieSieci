package kstcp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DaytimeTCPServer {
    private static final int DEFAULT_PORT = 13;
    private static final int DEFAULT_BACKLOG = 50;
    private static final String MODE_ITERATIVE = "iterative";
    private static final String MODE_CONCURRENT = "concurrent";

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        String mode = MODE_CONCURRENT;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam " + DEFAULT_PORT + ".");
                port = DEFAULT_PORT;
            }
        }
        if (args.length > 1) {
            String argMode = args[1].trim().toLowerCase();
            if (MODE_ITERATIVE.equals(argMode) || MODE_CONCURRENT.equals(argMode)) {
                mode = argMode;
            } else {
                System.err.println("Nieznany tryb '" + args[1] + "'. Uzywam '" + MODE_CONCURRENT + "'.");
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(port, DEFAULT_BACKLOG)) {
            System.out.println("DaytimeTCPServer uruchomiony na porcie " + port + " (tryb: " + mode + ")");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (MODE_CONCURRENT.equals(mode)) {
                    new ClientHandler(clientSocket).start();
                } else {
                    handleClient(clientSocket);
                }
            }
        } catch (IOException e) {
            System.err.println("Blad uruchomienia serwera: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (Socket client = socket;
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(client.getOutputStream(), StandardCharsets.US_ASCII), true)) {

            String payload = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
            out.print(payload + "\r\n");
            out.flush();
            System.out.println("Obsluzono klienta " + client.getRemoteSocketAddress() + " -> " + payload);
        } catch (IOException e) {
            System.err.println("Blad obslugi klienta: " + e.getMessage());
        }
    }

    private static final class ClientHandler extends Thread {
        private final Socket socket;

        private ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            handleClient(socket);
        }
    }
}
