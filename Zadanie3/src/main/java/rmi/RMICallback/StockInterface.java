/*
 *  Koszalin 2004
 *  Interfejs metod zdalnych serwera
 *  Dariusz Rataj (C)
 */

package rmi.RMICallback;

import java.rmi.*;

public interface StockInterface extends java.rmi.Remote {
  /* rejestracja obiektu klienta na liście do aktualizacji */
  void regCallback(StockUpdate obj) throws RemoteException;
  /* wyrejestrowanie obiektu klienta */
  void unregCallback(StockUpdate obj) throws RemoteException;
}

