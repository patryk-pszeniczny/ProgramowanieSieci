/*
 *  Koszalin 2002
 *  DateClient.java
 *  Klient wywolujacy zdalna metode zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */

package rmi.DateRmi;

import java.rmi.Naming;

public class DateClient {

  public static void main(String[] args) {
    String host = args.length > 0 ? args[0] : "127.0.0.1";
    int port = 1099;

    try {
      DateInterface remoteObject = (DateInterface) Naming.lookup("rmi://" + host + ":" + port + "/DateObject");
      String date = remoteObject.getDate();
      System.out.println("Data na zdalnym hoscie: " + date);
    } catch (Exception ex) {
      System.out.println("Blad wywolania obiektu: " + ex);
    }
  }
}

