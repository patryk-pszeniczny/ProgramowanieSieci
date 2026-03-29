package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoUdpServerIterative {
    public static void main(String[] args) {
        int port = 7;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam 7.");
                port = 7;
            }
        }

        System.out.println("EchoUdpServerIterative nasluchuje na porcie " + port);
        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (true) {
                DatagramPacket request = new DatagramPacket(new byte[2048], 2048);
                socket.receive(request);

                String payload = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8);
                System.out.println("Odebrano od " + request.getAddress().getHostAddress() + ":" + request.getPort()
                        + " [" + payload + "]");

                DatagramPacket response = new DatagramPacket(
                        request.getData(), request.getLength(), request.getAddress(), request.getPort());
                socket.send(response);
            }
        } catch (SocketException e) {
            System.err.println("Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad I/O: " + e.getMessage());
        }
    }
}
