/*
 *  Koszalin 2004
 *  SimpleServer.java
 *  Przyklad prostego serwera CHAT
 *  Dariusz Rataj (C)
 */

package kstcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    private static final int DEFAULT_PORT = 4444;
    private static final int DEFAULT_BACKLOG = 50;

    public static void main(String[] args) throws IOException {
        String fromClient = "", fromServer = "";
        int port = DEFAULT_PORT;
        int backlog = DEFAULT_BACKLOG;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam domyslnego " + DEFAULT_PORT + ".");
                port = DEFAULT_PORT;
            }
        }
        if (args.length > 1) {
            try {
                backlog = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy backlog '" + args[1] + "'. Uzywam domyslnego " + DEFAULT_BACKLOG + ".");
                backlog = DEFAULT_BACKLOG;
            }
        }

        ServerSocket serverSocket = null; // gniazdko serwera
        try {
            // zainicjowanie gniazdka na porcie nr 4444
            serverSocket = new ServerSocket(port, backlog);
            System.out.println("SimpleServer uruchomiony na porcie " + port + ", backlog=" + backlog);
        } catch (IOException e) {
            System.err.println("Nie moge oczekiwac na porcie " + port + "!");
            System.exit(1);
        }

        // stdIn - wej�cie standardowe (wej. konsoli java)
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            Socket clientSocket = null;  // gniazdko klienta
            System.out.println("Jestem Serwer:");
            System.out.println(" 'exit' - wyrzucenie Klienta i ponowne oczekiwanie");
            System.out.println(" 'quit' - zakonczenie pracy");
            System.out.println("Oczekiwanie na polaczenie...");
            try {
                clientSocket = serverSocket.accept(); // wlaczenie nasluchu i ew. nawiazanie polaczenia
            } catch (IOException e) {
                System.err.println("Blad Accept");
                System.exit(1);
            }
            // pobranie strumienia wyjsciowego klienta
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // pobranie strumienia wejsciowego klienta
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            while ((fromClient = in.readLine()) != null) // petla odczytu i zapisu do gniazdka
            {
                System.out.println("Client: " + fromClient); // wydruk na konsoli danych klienta
                if (fromClient.equals("exit"))  // zakonczenie przez klienta
                {
                    System.out.println("Client sie odlaczyl!!! ");
                    break;
                }
                if (fromServer.equals("quit")) // zakonczenie przez serwer
                    break;
                System.out.print("Server: ");
                fromServer = stdIn.readLine(); // pobranie danych z konsoli
                if (fromServer != null) {
                    out.println(fromServer); // wyslanie danych do klienta
                }
            }

            out.close(); // zamkniecie otwartych strumieni i gniazdka
            in.close();
            clientSocket.close();
            if (fromServer.equals("quit")) // zakonczenie przez serwer
                break;
        }

        stdIn.close(); // zamkniecie strumienia z konsoli i gniazdka serwera
        serverSocket.close();
    }
}
