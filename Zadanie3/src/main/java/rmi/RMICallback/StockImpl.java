package rmi.RMICallback;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class StockImpl extends UnicastRemoteObject implements StockInterface, Runnable {

  private final Vector<StockUpdate> clients = new Vector<>();

  public StockImpl() throws RemoteException {
    super();
  }

  @Override
  public void run() {
    double price = 22.0;

    while (true) {
      String name = "TPSA";
      for (StockUpdate client : clients) {
        try {
          client.updatePrice(name, ("" + price).substring(0, Math.min(5, ("" + price).length())));
        } catch (RemoteException ex) {
          try {
            unregCallback(client);
          } catch (RemoteException ignored) {
          }
        }
      }

      price += (Math.random() - 0.5d);

      try {
        Thread.sleep(1500);
      } catch (InterruptedException ignored) {
      }
    }
  }

  @Override
  public synchronized void regCallback(StockUpdate obj) throws RemoteException {
    if (!clients.contains(obj)) {
      clients.add(obj);
      System.out.println("Klient zarejestrowany: " + obj);
    }
  }

  @Override
  public synchronized void unregCallback(StockUpdate obj) throws RemoteException {
    clients.remove(obj);
    System.out.println("Klient wyrejestrowany: " + obj);
  }

  public static void main(String[] args) {
    int registryPort = 1099;

    try {
      try {
        LocateRegistry.createRegistry(registryPort);
        System.out.println("Uruchomiono lokalny rejestr RMI na porcie " + registryPort);
      } catch (RemoteException ignored) {
        System.out.println("Rejestr RMI juz dziala na porcie " + registryPort);
      }

      StockImpl stockObject = new StockImpl();
      Naming.rebind("rmi://127.0.0.1:" + registryPort + "/StockTpsa", stockObject);
      System.out.println("Obiekt StockImpl gotowy");

      Thread updateThread = new Thread(stockObject, "StockImplementation");
      updateThread.start();
    } catch (Exception ex) {
      System.err.println("Blad: " + ex);
      ex.printStackTrace();
    }
  }
}
