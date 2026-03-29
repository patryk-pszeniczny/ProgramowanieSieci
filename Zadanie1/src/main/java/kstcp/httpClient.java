/*
 *  Koszalin 2004
 *  httpClient.java
 *  Przyklad prostego klienta protokolu http
 *  Dariusz Rataj (C)
 */
package kstcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class httpClient {
    private static final String[] HOSTS = {
            "termit.ie.tu.koszalin.pl",
            "kos.man.koszalin.pl",
            "www.weii.tu.koszalin.pl"
    };
    private static final int PORT = 80;
    private static final int TIMEOUT_MS = 5000;
    private static final String PATH = "/";
    private static final Path OUTPUT_DIR = Paths.get("downloaded_pages");

    public static void main(String[] args) {
        int port = PORT;
        int timeoutMs = TIMEOUT_MS;
        String path = PATH;
        List<String> hosts = new ArrayList<>();

        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                try {
                    port = Integer.parseInt(arg.substring("--port=".length()));
                } catch (NumberFormatException e) {
                    System.err.println("Nieprawidlowy port: " + arg);
                    return;
                }
            } else if (arg.startsWith("--path=")) {
                path = arg.substring("--path=".length());
            } else if (arg.startsWith("--timeout=")) {
                try {
                    timeoutMs = Integer.parseInt(arg.substring("--timeout=".length()));
                } catch (NumberFormatException e) {
                    System.err.println("Nieprawidlowy timeout: " + arg);
                    return;
                }
            } else {
                hosts.add(arg);
            }
        }

        if (hosts.isEmpty()) {
            for (String host : HOSTS) {
                getDocument(host, port, path, timeoutMs);
                System.out.println();
            }
            return;
        }

        for (String host : hosts) {
            getDocument(host, port, path, timeoutMs);
            System.out.println();
        }
    }

    private static void getDocument(String host, int port, String path, int timeoutMs) {
        String request = buildRequest(host, path);
        StringBuilder body = new StringBuilder();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            socket.setSoTimeout(timeoutMs);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.print(request);
                out.flush();

                System.out.println("---------------- Komunikat http wyslany --------------------");
                System.out.println("Host: " + host + ":" + port + " Path: " + path);
                System.out.println("------------------ Odpowiedz serwera: ----------------------");

                String line;
                boolean inBody = false;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    if (inBody) {
                        body.append(line).append(System.lineSeparator());
                    } else if (line.isEmpty()) {
                        inBody = true;
                    }
                }
            }

            saveDocument(host, body.toString());
            System.out.println("------------------- Koniec odpowiedzi ----------------------");
        } catch (UnknownHostException e) {
            System.err.println("Blad URL dla hosta: " + host);
        } catch (SocketTimeoutException e) {
            System.err.println("Timeout dla hosta " + host + " po " + timeoutMs + " ms.");
        } catch (IOException e) {
            System.err.println("Blad I/O dla hosta " + host + ": " + e.getMessage());
        }
    }

    private static String buildRequest(String host, String path) {
        return "GET " + path + " HTTP/1.1\r\n"
                + "Host: " + host + "\r\n"
                + "Accept: */*\r\n"
                + "Connection: close\r\n"
                + "User-Agent: ProgramowanieSieci-httpClient/1.0\r\n"
                + "\r\n";
    }

    private static void saveDocument(String host, String documentBody) throws IOException {
        Files.createDirectories(OUTPUT_DIR);
        String fileName = host.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html";
        Path filePath = OUTPUT_DIR.resolve(fileName);
        Files.writeString(filePath, documentBody, StandardCharsets.UTF_8);
        System.out.println("Zapisano dokument do pliku: " + filePath.toAbsolutePath());
    }
}
