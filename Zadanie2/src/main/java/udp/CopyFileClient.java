/*
 *  Koszalin 2004
 *  CopyFileClient.java
 *  Przyklad klienta odbierajacego plik w kawalkach przez UDP
 *  Dariusz Rataj (C)
 */

package udp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class CopyFileClient {
    private static final int CHUNK_SIZE = 8192;

    public static void main(String[] args) {
        String serverHost = "127.0.0.1";
        int serverPort = 5601;
        Path targetFile = Path.of("Zadanie2/udp_test_files/copied.bin");

        long expectedSize = -1;

        receiveFile(serverHost, serverPort, targetFile, expectedSize);
    }

    private static void receiveFile(String serverHost, int serverPort, Path targetFile, long expectedSizeArg) {
        try {
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            try (DatagramSocket socket = new DatagramSocket();
                 FileOutputStream file = new FileOutputStream(targetFile.toFile())) {
                socket.setSoTimeout(50000);

                byte[] request = "GET".getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(request, request.length, serverAddress, serverPort));

                DatagramPacket headerPacket = new DatagramPacket(new byte[256], 256);
                socket.receive(headerPacket);
                String header = new String(headerPacket.getData(), 0, headerPacket.getLength(), StandardCharsets.UTF_8).trim();
                System.out.println("Naglowek od serwera: " + header);

                if (!header.startsWith("OK")) {
                    System.err.println("Serwer odrzucil zadanie kopiowania.");
                    return;
                }

                long serverSize = parseServerSize(header);
                long expectedSize = expectedSizeArg > 0 ? expectedSizeArg : serverSize;
                if (expectedSize <= 0) {
                    System.err.println("Brak poprawnego rozmiaru pliku.");
                    return;
                }

                long received = 0;
                int chunkNo = 0;
                while (received < expectedSize) {
                    int toRead = (int) Math.min(CHUNK_SIZE, expectedSize - received);
                    DatagramPacket packet = new DatagramPacket(new byte[toRead], toRead);
                    socket.receive(packet);
                    file.write(packet.getData(), 0, packet.getLength());
                    received += packet.getLength();
                    chunkNo++;
                    System.out.println("Odebrano kawalek " + chunkNo + ", bajtow=" + packet.getLength() + ", odebrano lacznie=" + received);

                    byte[] ack = "ACK".getBytes(StandardCharsets.UTF_8);
                    socket.send(new DatagramPacket(ack, ack.length, serverAddress, serverPort));
                }

                System.out.println("Koniec odbioru pliku. Zapisano do: " + targetFile.toAbsolutePath()
                        + ", rozmiar=" + received + " B");
            }
        } catch (UnknownHostException e) {
            System.err.println("Nieznany host: " + serverHost);
        } catch (SocketTimeoutException e) {
            System.err.println("Timeout oczekiwania na pakiet: " + e.getMessage());
        } catch (SocketException e) {
            System.err.println("Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad I/O: " + e.getMessage());
        }
    }

    private static long parseServerSize(String header) {
        String[] parts = header.split("\\s+");
        if (parts.length < 2) {
            return -1;
        }
        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
