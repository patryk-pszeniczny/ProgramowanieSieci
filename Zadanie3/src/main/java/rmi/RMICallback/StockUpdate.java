/*
 *  Koszalin 2004
 *  Interfejs metod zdalnych klienta
 *  Dariusz Rataj (C)
 */

import java.rmi.*;

public interface StockUpdate extends java.rmi.Remote {
  /* aktualizacja danych klienta */
  void updatePrice(String name, String price) throws RemoteException;
}
