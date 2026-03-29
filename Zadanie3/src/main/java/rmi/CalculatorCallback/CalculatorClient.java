package rmi.CalculatorCallback;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorClient extends UnicastRemoteObject implements CalculatorClientCallback {

  protected CalculatorClient() throws RemoteException {
    super();
  }

  @Override
  public double dodaj(double a, double b) throws RemoteException {
    return a + b;
  }

  @Override
  public double odejmij(double a, double b) throws RemoteException {
    return a - b;
  }

  @Override
  public double pomnoz(double a, double b) throws RemoteException {
    return a * b;
  }

  @Override
  public double podziel(double a, double b) throws RemoteException {
    if (b == 0.0d) {
      throw new RemoteException("Dzielenie przez zero.");
    }
    return a / b;
  }

  @Override
  public void showResult(String operacja, double a, double b, double wynik) throws RemoteException {
    System.out.println("Wynik [" + operacja + "] dla " + a + " i " + b + " = " + wynik);
  }

  @Override
  public void showError(String message) throws RemoteException {
    System.out.println("Blad: " + message);
  }

  public static void main(String[] args) {
    String host = args.length > 0 ? args[0] : "127.0.0.1";
    CalculatorClient clientCallback = null;

    try {
      clientCallback = new CalculatorClient();
      CalculatorService service =
          (CalculatorService) Naming.lookup("rmi://" + host + ":1099/CalculatorService");

      service.oblicz(10, 5, "dodaj", clientCallback);
      service.oblicz(10, 5, "odejmij", clientCallback);
      service.oblicz(10, 5, "pomnoz", clientCallback);
      service.oblicz(10, 0, "podziel", clientCallback);
    } catch (Exception ex) {
      System.out.println("Blad CalculatorClient: " + ex);
      ex.printStackTrace();
    } finally {
      try {
        if (clientCallback != null) {
          UnicastRemoteObject.unexportObject(clientCallback, true);
        }
      } catch (Exception ignored) {
      }
    }

    System.exit(0);
  }
}
