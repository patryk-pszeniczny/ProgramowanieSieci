package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoUdpServerConcurrent {
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

        System.out.println("EchoUdpServerConcurrent nasluchuje na porcie " + port);
        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (true) {
                DatagramPacket request = new DatagramPacket(new byte[2048], 2048);
                socket.receive(request);

                byte[] dataCopy = new byte[request.getLength()];
                System.arraycopy(request.getData(), 0, dataCopy, 0, request.getLength());
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                new EchoWorker(socket, dataCopy, clientAddress, clientPort).start();
            }
        } catch (SocketException e) {
            System.err.println("Blad gniazda UDP: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Blad I/O: " + e.getMessage());
        }
    }

    private static class EchoWorker extends Thread {
        private final DatagramSocket socket;
        private final byte[] data;
        private final InetAddress address;
        private final int port;

        private EchoWorker(DatagramSocket socket, byte[] data, InetAddress address, int port) {
            this.socket = socket;
            this.data = data;
            this.address = address;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                String payload = new String(data, StandardCharsets.UTF_8);
                System.out.println("[" + getName() + "] Odebrano od " + address.getHostAddress() + ":" + port
                        + " [" + payload + "]");
                DatagramPacket response = new DatagramPacket(data, data.length, address, port);
                synchronized (socket) {
                    socket.send(response);
                }
            } catch (IOException e) {
                System.err.println("[" + getName() + "] Blad wysylki: " + e.getMessage());
            }
        }
    }
}
