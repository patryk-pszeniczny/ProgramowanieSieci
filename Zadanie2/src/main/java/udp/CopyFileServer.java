/*
 *  Koszalin 2004
 *  CopyFileServer.java
 *  Przyklad serwera wysylajacego plik w kawalkach przez UDP
 *  Dariusz Rataj (C)
 */

package udp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFileServer {
    private static final int CHUNK_SIZE = 8192;
    private final int port;
    private final Path filePath;

    public CopyFileServer(Path filePath, int port) {
        this.filePath = filePath;
        this.port = port;
    }

    public void run() {
        if (!Files.exists(filePath)) {
            System.err.println("Nie znaleziono pliku: " + filePath.toAbsolutePath());
            return;
        }

        long fileSize;
        try {
            fileSize = Files.size(filePath);
        } catch (IOException e) {
            System.err.println("Nie mozna odczytac rozmiaru pliku: " + e.getMessage());
            return;
        }

        System.out.println("CopyFileServer nasluchuje na porcie " + port + ", plik: " + filePath.toAbsolutePath()
                + ", rozmiar=" + fileSize + " B");

        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setSoTimeout(50000);
            while (true) {
                DatagramPacket request = new DatagramPacket(new byte[256], 256);
                socket.receive(request);
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                String reqText = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8).trim();
                System.out.println("Zgloszenie od klienta " + clientAddress.getHostAddress() + ":" + clientPort
                        + " [" + reqText + "]");

                String header = "OK " + fileSize;
                byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(headerBytes, headerBytes.length, clientAddress, clientPort));

                sendFile(socket, clientAddress, clientPort, fileSize);
            }
        } catch (SocketException e) {
            System.err.println("Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad I/O: " + e.getMessage());
        }
    }

    private void sendFile(DatagramSocket socket, InetAddress clientAddress, int clientPort, long fileSize) {
        try (FileInputStream file = new FileInputStream(filePath.toFile())) {
            long sent = 0;
            int chunkNo = 0;
            byte[] buffer = new byte[CHUNK_SIZE];

            while (sent < fileSize) {
                int count = file.read(buffer);
                if (count < 0) {
                    break;
                }
                chunkNo++;
                DatagramPacket chunk = new DatagramPacket(buffer, count, clientAddress, clientPort);
                socket.send(chunk);
                sent += count;
                System.out.println("Wyslano kawalek " + chunkNo + ", bajtow=" + count + ", wyslano lacznie=" + sent);

                DatagramPacket ack = new DatagramPacket(new byte[64], 64);
                try {
                    socket.receive(ack);
                } catch (SocketTimeoutException ex) {
                    System.out.println("Brak ACK dla kawalka " + chunkNo + " (timeout), kontynuuje.");
                }
            }
            System.out.println("Koniec wysylania. Wyslano " + sent + " B.");
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku do wysylki: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad wysylania pliku: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        Path filePath = Path.of("Zadanie2/udp_test_files/source.bin");
        int port = 5601;

        new CopyFileServer(filePath, port).run();
    }
}
