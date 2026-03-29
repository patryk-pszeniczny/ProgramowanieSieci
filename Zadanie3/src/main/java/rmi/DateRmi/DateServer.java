/*
 *  Koszalin 2002
 *  DateServer.java
 *  Serwer udostepniajacy zdalny obiekt RMI
 *  Dariusz Rataj (C)
 */
package rmi.DateRmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DateServer {

  private static final int REGISTRY_PORT = 1099;

  public static void main(String[] args) {
    try {
      try {
        LocateRegistry.createRegistry(REGISTRY_PORT);
        System.out.println("Uruchomiono lokalny rejestr RMI na porcie " + REGISTRY_PORT);
      } catch (RemoteException ignored) {
        System.out.println("Rejestr RMI juz dziala na porcie " + REGISTRY_PORT);
      }

      DateImplementation remoteObject = new DateImplementation();
      Naming.rebind("rmi://127.0.0.1:" + REGISTRY_PORT + "/DateObject", remoteObject);
      System.out.println("Obiekt DateObject przygotowany");
    } catch (Exception ex) {
      System.out.println("Blad aktywacji obiektu: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}

