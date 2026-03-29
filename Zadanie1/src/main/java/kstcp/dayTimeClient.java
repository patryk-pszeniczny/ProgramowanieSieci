/*
 *  Koszalin 2004
 *  dayTimeClient.java
 *  Przyklad prostego klienta daty i czasu
 *  Dariusz Rataj (C)
 */
package kstcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class dayTimeClient {

    private static final String[] DEFAULT_HOSTS = {
            "localhost",
            "vega.ck.poznan.pl",
            "moskit.ie.tu.koszalin.pl"
    };
    private static final int DEFAULT_TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String[] hosts = DEFAULT_HOSTS;
        int firstPort = 13;
        int secondPort = 80;
        int timeoutMs = DEFAULT_TIMEOUT_MS;

        if (args.length > 0 && args[0] != null && !args[0].isBlank()) {
            hosts = args[0].split(",");
        }
        if (args.length > 1) {
            try {
                firstPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Nieprawidlowy pierwszy port '" + args[1] + "'. Uzywam 13.");
                firstPort = 13;
            }
        }
        if (args.length > 2) {
            try {
                secondPort = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Nieprawidlowy drugi port '" + args[2] + "'. Uzywam 80.");
                secondPort = 80;
            }
        }
        if (args.length > 3) {
            try {
                timeoutMs = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Nieprawidlowy timeout '" + args[3] + "'. Uzywam " + DEFAULT_TIMEOUT_MS + " ms.");
                timeoutMs = DEFAULT_TIMEOUT_MS;
            }
        }

        System.out.println("=== Port " + firstPort + " ===");
        uruchomDlaListyHostow(hosts, firstPort, timeoutMs);

        System.out.println("\n=== Port " + secondPort + " ===");
        uruchomDlaListyHostow(hosts, secondPort, timeoutMs);
    }  // koniec main

    private static void uruchomDlaListyHostow(String[] hosts, int port, int timeoutMs) {
        for (String host : hosts) {
            String trimmedHost = host.trim();
            if (!trimmedHost.isEmpty()) {
                pobierzDateICzas(trimmedHost, port, timeoutMs);
            }
            System.out.println();
        }
    }

    private static void pobierzDateICzas(String host, int port, int timeoutMs) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            socket.setSoTimeout(timeoutMs);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String time = in.readLine();
                System.out.println("Host: " + host + " port: " + port);
                if (time != null) {
                    System.out.println("Wynik: date i czas: " + time);
                } else {
                    System.out.println("Wynik: Brak danych (polaczenie zamkniete przez serwer).");
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Host: " + host + " port: " + port);
            System.out.println("Wynik: Nieznany host.");
        } catch (SocketTimeoutException e) {
            System.out.println("Host: " + host + " port: " + port);
            System.out.println("Wynik: Timeout po " + timeoutMs + " ms.");
        } catch (IOException e) {
            System.out.println("Host: " + host + " port: " + port);
            System.out.println("Wynik: Blad I/O: " + e.getMessage());
        }
    }
} // koniec dayTimeClient
