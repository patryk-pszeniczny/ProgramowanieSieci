package rmi.CalculatorCallback;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorClientCallback extends Remote {
  double dodaj(double a, double b) throws RemoteException;

  double odejmij(double a, double b) throws RemoteException;

  double pomnoz(double a, double b) throws RemoteException;

  double podziel(double a, double b) throws RemoteException;

  void showResult(String operacja, double a, double b, double wynik) throws RemoteException;

  void showError(String message) throws RemoteException;
}
