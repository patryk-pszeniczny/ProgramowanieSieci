/*
 *  Koszalin 2004
 *  DatagramServer.java
 *  Przyklad serwera uslugi daytime - UDP
 *  Dariusz Rataj (C)
 */

package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DatagramServer {
    public static void main(String[] args) {
        int port = 13;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam 13.");
                port = 13;
            }
        }

        System.out.println("DatagramServer (daytime) nasluchuje na porcie " + port);
        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (true) {
                byte[] req = new byte[1024];
                DatagramPacket packet = new DatagramPacket(req, req.length);
                socket.receive(packet);
                String requestData = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();
                System.out.println("Odebrano datagram od " + packet.getAddress().getHostAddress() + ":" + packet.getPort()
                        + " [" + requestData + "]");

                byte[] resp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)
                        .getBytes(StandardCharsets.UTF_8);
                DatagramPacket responsePacket = new DatagramPacket(resp, resp.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
                System.out.println("Odeslano datagram: [" + new String(resp, StandardCharsets.UTF_8) + "]");
            }
        } catch (SocketException e) {
            System.err.println("Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad I/O: " + e.getMessage());
        }
    }
}
