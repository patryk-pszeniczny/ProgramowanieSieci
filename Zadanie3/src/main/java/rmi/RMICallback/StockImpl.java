/*
 *  Koszalin 2004
 *  Server Stock - Callback Demo
 *  Aplikacja 3 funkcje w jednym: Server + obiekt zdalny + aktywacja RMI Registy
 *  Dariusz Rataj (C)
 */

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.util.*;

public class StockImpl extends UnicastRemoteObject implements StockInterface, Runnable {

  /* Lista klientow*/
  private Vector clients = new Vector();

  public StockImpl() throws RemoteException { }

  public void run() {
    double price = 22.0;

    while (true) {
      String name = "TPSA";
      for (Enumeration e = clients.elements(); e.hasMoreElements(); ) {
       StockUpdate client = (StockUpdate) e.nextElement();
        try {
          client.updatePrice(name, (""+ price).substring(0,5)); // wywolanie metody zdalnej
        }
        catch (RemoteException ex) {
         System.out.println("Blad !? A moze sie rozlaczyl...?");
         try {
          unregCallback(client); // wyrejestrowanie klienta gdy sie wylaczy
          }
          catch (RemoteException exc) {
           System.err.println("Blad: " + exc);
           System.exit(2);
         }
        }
      }
    price += (Math.random()-0.5d);

    try {
      Thread.sleep(1500); // 1s spania
    } catch (InterruptedException iex) { }
  } // while
 } // run
  
  /* metoda zdalna - rejestracja klienta na liœcie do aktualizacji danych*/
  public synchronized void regCallback(StockUpdate obj) throws RemoteException {
    if (!(clients.contains(obj))) { // czy klienta na liscie
      clients.addElement(obj);
      System.out.println("Klient zarejestrowany: " + obj);
    }
  }
  
  /* metoda zdalna - wyrejestrowanie klienta */
  public synchronized void unregCallback(StockUpdate obj) throws RemoteException {
    clients.removeElement(obj);
    System.out.println("Klient wyrejestrowany z listy: " + obj);
  }

  public static void main(String args[]) {
    System.setSecurityManager(new RMISecurityManager());
    try {
	   LocateRegistry.createRegistry(1099); // aktywacja RMI Registry
      StockImpl stockObject = new StockImpl();
      Naming.rebind("rmi://127.0.0.1:1099/StockTpsa", stockObject);
      System.out.println("Obiekt StockImpl gotowy");
      /* watek aplikacji aktualizujacy dane */
      Thread updateThread = new Thread(stockObject, "StockImplementation");
      updateThread.start();
    } catch (Exception ex) {
       System.err.println("Blad: " + ex);
    }
  }
}
