package rmi.CalculatorCallback;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorService extends Remote {
  void oblicz(double a, double b, String operacja, CalculatorClientCallback callback)
      throws RemoteException;
}
