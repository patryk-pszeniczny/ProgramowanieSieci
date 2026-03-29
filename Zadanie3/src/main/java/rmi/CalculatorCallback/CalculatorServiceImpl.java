package rmi.CalculatorCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServiceImpl extends UnicastRemoteObject implements CalculatorService {

  public CalculatorServiceImpl() throws RemoteException {
    super();
  }

  @Override
  public synchronized void oblicz(
      double a, double b, String operacja, CalculatorClientCallback callback) throws RemoteException {
    String op = operacja == null ? "" : operacja.trim().toLowerCase();

    try {
      double wynik;
      switch (op) {
        case "dodaj":
        case "+":
          wynik = callback.dodaj(a, b);
          break;
        case "odejmij":
        case "-":
          wynik = callback.odejmij(a, b);
          break;
        case "pomnoz":
        case "mnoz":
        case "*":
          wynik = callback.pomnoz(a, b);
          break;
        case "podziel":
        case "dziel":
        case "/":
          wynik = callback.podziel(a, b);
          break;
        default:
          callback.showError("Nieznana operacja: " + operacja);
          return;
      }

      System.out.println("Serwer: oblicz(" + a + ", " + b + ", " + operacja + ")");
      callback.showResult(operacja, a, b, wynik);
    } catch (RemoteException ex) {
      try {
        callback.showError("Blad przy wywolaniu callback: " + ex.getMessage());
      } catch (RemoteException ignored) {
      }
    }
  }
}
