package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpReplyServer {
    public static void main(String[] args) {
        int port = 7010;

        System.out.println("UdpReplyServer nasluchuje na porcie " + port);
        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (true) {
                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                socket.receive(request);

                String text = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8).trim();
                String responseText = buildResponse(text);
                byte[] resp = responseText.getBytes(StandardCharsets.UTF_8);
                DatagramPacket response = new DatagramPacket(resp, resp.length, request.getAddress(), request.getPort());
                socket.send(response);

                System.out.println("Odebrano [" + text + "] -> odeslano [" + responseText + "]");
            }
        } catch (SocketException e) {
            System.err.println("Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad I/O: " + e.getMessage());
        }
    }

    private static String buildResponse(String request) {
        String prefix = "Komunikat\\";
        if (!request.startsWith(prefix)) {
            return "BlednyFormat";
        }
        String id = request.substring(prefix.length());
        return "Odpowiedz\\" + id;
    }
}
