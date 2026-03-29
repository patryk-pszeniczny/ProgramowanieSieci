/*
 *  Koszalin 2002
 *  DateInterface.java
 *  Interfejs obiektu zdalnego RMI
 *  Dariusz Rataj (C)
 */
package rmi.DateRmi;
import java.rmi.*;

public interface DateInterface extends Remote {

   /* deklaracja metody zdalnej */
   public String getDate() throws RemoteException;
   
 }  // DateInterface
