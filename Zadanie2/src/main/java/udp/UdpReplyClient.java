package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class UdpReplyClient {
    public static void main(String[] args) {
        // 127.0.0.1 7010 5
        String serverHost = "127.0.0.1";
        int serverPort = 7010;
        int n = 5;

        int receivedCount = 0;

        try {
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            try (DatagramSocket socket = new DatagramSocket()) {
                for (int i = 1; i <= n; i++) {
                    String id = String.format("%02d", i);
                    String requestText = "Komunikat\\" + id;
                    byte[] req = requestText.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket request = new DatagramPacket(req, req.length, serverAddress, serverPort);
                    socket.send(request);
                    System.out.println("Wyslano: " + requestText);

                    String expectedResponse = "Odpowiedz\\" + id;
                    long deadline = System.currentTimeMillis() + 5000;
                    boolean matched = false;

                    while (System.currentTimeMillis() < deadline) {
                        int remain = (int) Math.max(1, deadline - System.currentTimeMillis());
                        socket.setSoTimeout(Math.min(remain, 500));
                        DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
                        try {
                            socket.receive(response);
                            String responseText = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8).trim();
                            if (expectedResponse.equals(responseText)) {
                                receivedCount++;
                                matched = true;
                                System.out.println("Odebrano: " + responseText + " (dopasowany)");
                                break;
                            } else {
                                System.out.println("Odebrano: " + responseText + " (zignorowany)");
                            }
                        } catch (SocketTimeoutException ex) {
                            // petla sprawdza, czy minal juz deadline
                        }
                    }

                    if (!matched) {
                        System.out.println("Brak odpowiedzi na pakiet " + id + " w 5 sekund.");
                    }
                }
            }
            System.out.println("Wyslano " + n + " i otrzymano " + receivedCount + " pakietow");
        } catch (IOException e) {
            System.err.println("Blad klienta UDP: " + e.getMessage());
        }
    }
}
