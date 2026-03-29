package rmi.DBrmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DBServer {

  private static final int REGISTRY_PORT = 1099;

  public static void main(String[] args) {
    try {
      try {
        LocateRegistry.createRegistry(REGISTRY_PORT);
        System.out.println("Uruchomiono lokalny rejestr RMI na porcie " + REGISTRY_PORT);
      } catch (RemoteException ignored) {
        System.out.println("Rejestr RMI juz dziala na porcie " + REGISTRY_PORT);
      }

      DBImplementation remoteObject = new DBImplementation();
      Naming.rebind("rmi://127.0.0.1:" + REGISTRY_PORT + "/DatabaseObject", remoteObject);
      System.out.println("Obiekt DatabaseObject przygotowany... czekam...");
    } catch (Exception ex) {
      System.out.println("Blad aktywacji obiektu: " + ex);
      ex.printStackTrace();
    }
  }
}
