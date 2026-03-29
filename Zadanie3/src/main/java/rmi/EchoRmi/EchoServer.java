package rmi.EchoRmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class EchoServer {

  private static final int REGISTRY_PORT = 1099;

  public static void main(String[] args) {
    try {
      try {
        LocateRegistry.createRegistry(REGISTRY_PORT);
        System.out.println("Uruchomiono lokalny rejestr RMI na porcie " + REGISTRY_PORT);
      } catch (RemoteException ignored) {
        System.out.println("Rejestr RMI juz dziala na porcie " + REGISTRY_PORT);
      }

      EchoImplementation echoObject = new EchoImplementation();
      Naming.rebind("rmi://127.0.0.1:" + REGISTRY_PORT + "/EchoService", echoObject);
      System.out.println("EchoService gotowy (usluga echo - odpowiednik portu 7).");
    } catch (Exception ex) {
      System.out.println("Blad uruchomienia EchoServer: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}
