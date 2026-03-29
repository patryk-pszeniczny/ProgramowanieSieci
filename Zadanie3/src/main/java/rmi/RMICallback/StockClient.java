/*
 *  Koszalin 2004
 *  Klient Stock - Callback Demo
 *  Dariusz Rataj (C)
 */
 
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class StockClient implements StockUpdate {

  public static void main(String args[]) {
    StockClient client = new StockClient();
    try {
      UnicastRemoteObject.exportObject(client);  // aktywacja metod zd. obiektu
      StockInterface stockObject = (StockInterface)Naming.lookup("rmi://127.0.0.1:1099/StockTpsa");
      stockObject.regCallback(client);
    } catch (Exception ex) {
      System.err.println("Blad: " + ex);
      System.exit(2);
    }
  }
  /* metoda zdalna */
  public synchronized void updatePrice(String name, String price) throws RemoteException {
    System.out.println(" Aktualizacja danych: papier " + name + " = " + price + " zl");
  }
}

