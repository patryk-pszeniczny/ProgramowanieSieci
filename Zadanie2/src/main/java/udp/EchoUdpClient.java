package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class EchoUdpClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 7;
        int count = 3;
        String message = "Echo test";

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy port '" + args[1] + "'. Uzywam 7.");
                port = 7;
            }
        }
        if (args.length > 2) {
            try {
                count = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowa liczba pakietow '" + args[2] + "'. Uzywam 5.");
                count = 5;
            }
        }
        if (args.length > 3) {
            message = args[3];
        }

        try {
            InetAddress serverAddress = InetAddress.getByName(host);
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(5000);
                for (int i = 1; i <= count; i++) {
                    String payload = message + " #" + i;
                    byte[] data = payload.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket request = new DatagramPacket(data, data.length, serverAddress, port);
                    socket.send(request);

                    DatagramPacket response = new DatagramPacket(new byte[2048], 2048);
                    try {
                        socket.receive(response);
                        String echoed = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
                        System.out.println("Odebrano echo: [" + echoed + "]");
                    } catch (SocketTimeoutException e) {
                        System.out.println("Timeout oczekiwania na echo dla pakietu #" + i);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Blad klienta echo UDP: " + e.getMessage());
        }
    }
}
