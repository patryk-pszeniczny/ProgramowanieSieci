/*
 *  Koszalin 2004
 *  SimpleClient.java
 *  Przyklad prostego klienta CHAT
 *  Dariusz Rataj (C)
 */

package kstcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {
    private static final String DEFAULT_HOST = "192.168.66.5";
    private static final int DEFAULT_PORT = 4444;

    public static void main(String[] args) throws IOException {
        Socket socket = null;    // gniazdko
        PrintWriter out = null;    // strumien wyjsciowy - dane wysylane na server
        BufferedReader in = null;  // strumien wejsciowy - dane czytane z servera
        String fromServer, fromUser;
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy port '" + args[1] + "'. Uzywam domyslnego " + DEFAULT_PORT + ".");
                port = DEFAULT_PORT;
            }
        }

        try {
            // zainicjowanie gniazdka
            socket = new Socket(host, port);
            System.out.println("Polaczono z serwerem " + host + ":" + port);
            System.out.println("Czekam na wiadomosc z Servera...");

            // pobranie strumienia wyjsciowego serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // pobranie strumienia wejsciowego serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Nieznany adres hosta");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Blad operacji Wejscia/Wyjscia - I/O error");
            System.exit(1);
        }

        // stdIn - wej�cie standardowe (wej. konsoli java)
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        out.println("Jest na linii! Pisz Serwerze!!!");

        while ((fromServer = in.readLine()) != null) // petla odczytu i zapisu do gniazdka
        {
            System.out.println("Server: " + fromServer);  // wydruk na konsoli danych z serwera
            if (fromServer.equals("exit") || fromServer.equals("quit")) // zakonczenie przez serwer
            {
                System.out.println("Zostalem wyrzucony przez Server!");
                break;
            }
            System.out.print("Client: ");
            fromUser = stdIn.readLine(); // pobranie danych z konsoli
            if (fromUser != null) {
                out.println(fromUser);  // wyslanie danych do serwera
            }
        }
        out.close();      // zamkniecie otwartych strumieni i gniazdka
        in.close();
        stdIn.close();
        socket.close();
    }
}
