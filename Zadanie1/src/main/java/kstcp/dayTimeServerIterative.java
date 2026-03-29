/*
 *  dayTimeServerIterative.java
 *  Iteracyjny serwer uslugi daytime (RFC 867)
 */
package kstcp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class dayTimeServerIterative {
    private static final int DEFAULT_PORT = 13;
    private static final int DEFAULT_BACKLOG = 50;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        int backlog = DEFAULT_BACKLOG;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam " + DEFAULT_PORT + ".");
                port = DEFAULT_PORT;
            }
        }
        if (args.length > 1) {
            try {
                backlog = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy backlog '" + args[1] + "'. Uzywam " + DEFAULT_BACKLOG + ".");
                backlog = DEFAULT_BACKLOG;
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(port, backlog)) {
            System.out.println("dayTimeServerIterative uruchomiony na porcie " + port + ", backlog=" + backlog);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Blad uruchomienia serwera daytime: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (Socket socket = clientSocket;
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.US_ASCII), true)) {
            String daytimeText = buildDaytimeResponse();
            out.print(daytimeText + "\r\n");
            out.flush();
            System.out.println("Obsluzono klienta: " + socket.getRemoteSocketAddress() + " -> " + daytimeText);
        } catch (IOException e) {
            System.err.println("Blad obslugi klienta: " + e.getMessage());
        }
    }

    private static String buildDaytimeResponse() {
        return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }
}
