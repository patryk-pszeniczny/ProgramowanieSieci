/*
 *  Koszalin 2004
 *  SendDatagram.java
 *  Przyklad prostej aplikacji wysylajacej datagramy
 *  Dariusz Rataj (C)
 */

package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class SendDatagram {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5500;
        int packets = 10;
        int delayMs = 1000;

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy port '" + args[1] + "'. Uzywam 5500.");
                port = 5500;
            }
        }
        if (args.length > 2) {
            try {
                packets = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowa liczba pakietow '" + args[2] + "'. Uzywam 10.");
                packets = 10;
            }
        }
        if (args.length > 3) {
            try {
                delayMs = Integer.parseInt(args[3]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowe opoznienie '" + args[3] + "'. Uzywam 1000 ms.");
                delayMs = 1000;
            }
        }

        System.out.println("SendDatagram -> " + host + ":" + port + ", pakiety=" + packets + ", opoznienie=" + delayMs + " ms");
        try {
            InetAddress servAddr = InetAddress.getByName(host);
            try (DatagramSocket socket = new DatagramSocket()) {
                for (int i = 0; i < packets; i++) {
                    String data = i + ". Data i godzina wyslania: " + new Date();
                    byte[] buf = data.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, servAddr, port);
                    socket.send(packet);
                    System.out.println("Wyslano datagram #" + (i + 1));

                    if (delayMs > 0) {
                        try {
                            Thread.sleep(delayMs);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
