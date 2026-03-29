/*
 *  Koszalin 2004
 *  DatagramClient.java
 *  Przyklad klienta uslugi daytime - UDP
 *  Dariusz Rataj (C)
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class DatagramClient {
    private static final String[] DEFAULT_HOSTS = {
            "moskit.ie.tu.koszalin.pl",
            "kos.man.koszalin.pl",
            "vega.ck.poznan.pl",
            "wp.pl"
    };

    public static void main(String[] args) {
        int port = 13;
        int timeoutMs = 5000;

        String[] hosts = DEFAULT_HOSTS;
        if (args.length > 0) {
            hosts = args[0].split(",");
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy port '" + args[1] + "'. Uzywam 13.");
                port = 13;
            }
        }
        if (args.length > 2) {
            try {
                timeoutMs = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy timeout '" + args[2] + "'. Uzywam 5000.");
                timeoutMs = 5000;
            }
        }

        System.out.println("DatagramClient: port=" + port + ", timeout=" + timeoutMs + " ms");
        for (String host : hosts) {
            queryHost(host.trim(), port, timeoutMs);
            System.out.println();
        }
    }

    private static void queryHost(String host, int port, int timeoutMs) {
        if (host.isEmpty()) {
            return;
        }
        try {
            InetAddress servAddr = InetAddress.getByName(host);
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(timeoutMs);

                byte[] req = new byte[1]; // pusty datagram uslugi daytime
                socket.send(new DatagramPacket(req, req.length, servAddr, port));
                System.out.println("[" + host + "] Wyslano datagram daytime na port " + port);

                byte[] resp = new byte[256];
                DatagramPacket responsePacket = new DatagramPacket(resp, resp.length);
                socket.receive(responsePacket);
                String payload = new String(responsePacket.getData(), 0, responsePacket.getLength(), StandardCharsets.UTF_8).trim();
                System.out.println("[" + host + "] Odpowiedz: " + payload);
            }
        } catch (UnknownHostException e) {
            System.err.println("[" + host + "] Nieznany host.");
        } catch (SocketTimeoutException e) {
            System.err.println("[" + host + "] Timeout po " + timeoutMs + " ms.");
        } catch (SocketException e) {
            System.err.println("[" + host + "] Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[" + host + "] Blad I/O: " + e.getMessage());
        }
    }
}
