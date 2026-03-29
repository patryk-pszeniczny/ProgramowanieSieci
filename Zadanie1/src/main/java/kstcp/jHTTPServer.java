/*
 *  Koszalin 2004
 *  jHTTPServer.java
 *  Przyklad iteracyjnego serwera http
 *  Dariusz Rataj (C)
 */
package kstcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class jHTTPServer {

    private int port = 80;

    public jHTTPServer(int port) {
        this.port = port;
        Socket socket = null;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("jHTTPServer uruchomiony na porcie " + port);
            while (true) {
                socket = server.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                System.out.println("---------------- Pierwsza linia zapytania --------------------");
                System.out.println(in.readLine());
                System.out.println("---------------- Wysylam odpowiedz ---------------------------");
                System.out.println(getAnswer());
                System.out.println("---------------- Koniec odpowiedzi ---------------------------");
                out.println(getAnswer());
                out.flush();
                socket.close();
                if (socket != null) socket.close();
            }
        } catch (IOException e) {
            System.out.println("Blad otwarcia portu " + port + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 80;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Nieprawidlowy port '" + args[0] + "'. Uzywam domyslnego 80.");
            }
        }
        new jHTTPServer(port);
    }

    String getAnswer() {

        InetAddress adres;
        String name = "";
        String ip = "";

        try {
            adres = InetAddress.getLocalHost();
            name = adres.getHostName();
            ip = adres.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println(e);
        }

        String document = "<html>\r\n" +
                "<body><br>\r\n" +
                "<h2><font color=red>jHTTPServer demo document\r\n" +
                "</font></h2>\r\n" +
                "<h3>Server iteracyjny</h3><hr>\r\n" +
                "Data: <b>" + new Date() + "</b><br>\r\n" +
                "Nazwa hosta: <b>" + name + "</b><br>\r\n" +
                "IP hosta: <b>" + ip + "</b><br>\r\n" +
                "<hr>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";

        String header = "HTTP/1.1 200 OK\r\n" +
                "Server: jHTTPServer ver 1.1\r\n" +
                "Last-Modified: Fri, 28 Jul 2000 07:58:55 GMT\r\n" +
                "Content-Length: " + document.length() + "\r\n" +
                "Connection: close\r\n" +
                "Content-Type: text/html";

        return header + "\r\n\r\n" + document;
    }

}
