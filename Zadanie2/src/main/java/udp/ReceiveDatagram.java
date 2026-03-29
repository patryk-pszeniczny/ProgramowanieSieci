/*
 *  Koszalin 2004
 *  ReceiveDatagram.java
 *  Przyklad prostej aplikacji odbierajacej datagramy
 *  Dariusz Rataj (C)
 */
package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ReceiveDatagram {

    public static void main(String[] args) {
        int port = 5500;
        int maxPackets = -1; // -1 = bez limitu

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam 5500.");
                port = 5500;
            }
        }
        if (args.length > 1) {
            try {
                maxPackets = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidlowy limit pakietow '" + args[1] + "'. Uzywam bez limitu.");
                maxPackets = -1;
            }
        }

        System.out.println("ReceiveDatagram nasluchuje na porcie " + port
                + (maxPackets > 0 ? ", limit pakietow=" + maxPackets : ", bez limitu"));
        int received = 0;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            while (true) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                received++;

                String data = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();
                InetAddress senderAddress = packet.getAddress();
                int senderPort = packet.getPort();
                System.out.println("Pakiet #" + received + " od "
                        + senderAddress.getHostAddress() + ":" + senderPort + " [" + data + "]");

                if (maxPackets > 0 && received >= maxPackets) {
                    System.out.println("Osiagnieto limit pakietow. Koniec.");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
