package rmi.CalculatorCallback;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class CalculatorServer {

  private static final int REGISTRY_PORT = 1099;

  public static void main(String[] args) {
    try {
      try {
        LocateRegistry.createRegistry(REGISTRY_PORT);
        System.out.println("Uruchomiono lokalny rejestr RMI na porcie " + REGISTRY_PORT);
      } catch (RemoteException ignored) {
        System.out.println("Rejestr RMI juz dziala na porcie " + REGISTRY_PORT);
      }

      CalculatorService service = new CalculatorServiceImpl();
      Naming.rebind("rmi://127.0.0.1:" + REGISTRY_PORT + "/CalculatorService", service);
      System.out.println("CalculatorService gotowy.");
    } catch (Exception ex) {
      System.out.println("Blad uruchomienia CalculatorServer: " + ex.getMessage());
      ex.printStackTrace();
    }
  }
}
