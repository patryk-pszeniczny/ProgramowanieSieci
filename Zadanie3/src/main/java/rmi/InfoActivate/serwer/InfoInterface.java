/*
 *  Koszalin 2003
 *  InfoInterface.java
 *  Interfejs obiektu zdalnego RMI
 *  Dariusz Rataj (C)
 */

import java.rmi.*;

public interface InfoInterface extends Remote {

   /* deklaracja metod zdalnych */
   public String getDate() throws RemoteException;
   public String getOSInfo() throws RemoteException;
   public String getJVMInfo() throws RemoteException;
   
 }  // InfoInterface