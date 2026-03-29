/*
 *  Koszalin 2003
 *  DBInterface.java
 *  Interfejs obiektu zdalnego RMI oblugi baz danych
 *  Dariusz Rataj (C)
 */

import java.util.*;
import java.rmi.*;

public interface DBInterface extends Remote {

   /* deklaracja metod zdalnych */
   public void connectDatabase() throws RemoteException;
   public void disconnectDatabase() throws RemoteException;
   public Vector getColumns(String table) throws RemoteException;
   public Vector getTableData(String table) throws RemoteException;
   public int getColumnCount(String table) throws RemoteException;

 }  // DateInterface