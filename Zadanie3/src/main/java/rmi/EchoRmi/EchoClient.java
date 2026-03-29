package rmi.EchoRmi;

import java.rmi.Naming;

public class EchoClient {

  public static void main(String[] args) {
    String host = args.length > 0 ? args[0] : "127.0.0.1";
    String message = args.length > 1 ? args[1] : "To jest test uslugi echo przez RMI.";

    try {
      EchoInterface echoService =
          (EchoInterface) Naming.lookup("rmi://" + host + ":1099/EchoService");
      String response = echoService.echo(message);

      System.out.println("Wyslano:  " + message);
      System.out.println("Odebrano: " + response);
    } catch (Exception ex) {
      System.out.println("Blad EchoClient: " + ex);
      ex.printStackTrace();
    }
  }
}
