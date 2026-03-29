/*
 *  Koszalin 2004
 *  smtpClient.java
 *  Dariusz Rataj (C)
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class smtpClient {

    // obiekt gniazda i strumienie I/O
    Socket socket = null;              // gniazdo
    PrintWriter out = null;         // input
    BufferedReader in = null; // output


    // konstruktor: argumenty <><><>
    public smtpClient(String host,
                      String strFrom,
                      String strTo,
                      String subject,
                      String message) throws IOException {

        // pr�ba nawi�zania po��czenia z serwerem SMTP
        try {
            socket = new Socket(host, 25);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Nieznany host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Problem z polaczeniem z " + host);
            System.exit(1);
        }
        recvText();
        sendText("HELO smtpClient");//"powitanie"
        recvText();
        sendText("MAIL FROM: " + strFrom);//wys�anie nadawcy
        recvText();
        sendText("RCPT TO: " + strTo);//wys�anie odbiorcy
        recvText();
        sendText("DATA");              //przesy�anie danych (tresci listu)
        recvText();
        sendText("Subject: " + subject); //wys�anie tematu
//    sendText("Cc: \"Telewizja Polska\"<biuro@tvp.pl>"); //przes�anie na inny adres
//    sendText("Reply-to: "+strTo+"\n"); // adres zwrotny wiadomo�ci
        sendText(message);   // a potem wiadomo�ci
        sendText(".");       // linia "." - informacja dla serwera o zakonczeniu tresci
        recvText();
        sendText("QUIT");    // zako�czenie
        recvText();
        socket.close();      // zamkni�cie po��czenia
    }

    public static void main(String[] args) throws IOException {

        String host = "moskit.ie.tu.koszalin.pl";
        String strFrom = "\"Ktos\"<jolka@moskit.ie.tu.koszalin.pl>";
        String strTo = "\"Jozek \"<jozek@kos.man.koszalin.pl>";
        String subject = "Proba maila 1";
        String message = "To jest pr�ba wiadomosci z moskita \n" +
                "na kosa. Tresc tej wiadomosci nie ma\n" +
                "wi�kszego znaczenia ale dobrze �e jaka� jest";

        new smtpClient(host, strFrom, strTo, subject, message);
    }

    // wysylanie komunikatow przez strumien out
    public void sendText(String text) {
        out.println(text);
        System.out.println(" K: " + text);
    }

    // odbieranie komunikatow przez strumien in
    public String recvText() throws IOException {
        String odp = in.readLine();
        System.out.println(" S: " + odp);
        return odp;
    }
}
