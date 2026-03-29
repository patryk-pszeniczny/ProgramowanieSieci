package rmi.RMICallback;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StockClient implements StockUpdate {

  public static void main(String[] args) {
    StockClient client = new StockClient();
    try {
      UnicastRemoteObject.exportObject(client, 0);
      StockInterface stockObject =
          (StockInterface) Naming.lookup("rmi://127.0.0.1:1099/StockTpsa");
      stockObject.regCallback(client);
      System.out.println("Klient callback podlaczony. Czekam na aktualizacje...");
    } catch (Exception ex) {
      System.err.println("Blad: " + ex);
      ex.printStackTrace();
      System.exit(2);
    }
  }

  @Override
  public synchronized void updatePrice(String name, String price) throws RemoteException {
    System.out.println("Aktualizacja danych: papier " + name + " = " + price + " zl");
  }
}
