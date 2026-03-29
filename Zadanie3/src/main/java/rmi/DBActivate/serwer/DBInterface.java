/*
 *  Koszalin 2003
 *  DBInterface.java
 *  Interfejs obiektu zdalnego RMI
 *  Dariusz Rataj (C)
 */

package rmi.DBActivate.serwer;

import java.rmi.*;
import java.util.*;

public interface DBInterface extends Remote {

   /* deklaracja metod zdalnych */
   public void connectDatabase() throws RemoteException;
   public void disconnectDatabase() throws RemoteException;
   public Vector getColumns(String table) throws RemoteException;
   public Vector getTableData(String table) throws RemoteException;
   public int getColumnCount(String table) throws RemoteException;
   
 }  // DBInterface
