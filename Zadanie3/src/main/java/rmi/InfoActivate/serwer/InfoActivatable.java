/*
 *  Koszalin 2003
 *  InfoActivatable.java
 *  Implementacja klasy zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */
 
package rmi.InfoActivate.serwer;

import java.rmi.*;
import java.rmi.activation.*;
import java.util.*;


public class InfoActivatable extends Activatable implements InfoInterface {

 
 public InfoActivatable(ActivationID id, MarshalledObject data) throws RemoteException {
   super(id, 0); // wywołanie konstruktora klasy Activatable
 }

 /* implementacja metody zdalnej */
 public String getDate() throws RemoteException {
  Date date = new Date();
  System.out.println("\nZdalne wywolanie metody getDate() klasy InfoActivatable");
  return date.toString();
 } // getDate
 
 public String getOSInfo() throws RemoteException {
  String info = System.getProperty("os.name") + ", "+ System.getProperty("os.version");
  System.out.println("\nZdalne wywolanie metody getOSInfo() klasy InfoActivatable");
  return info;
 } // getOSInfo
 
 public String getJVMInfo() throws RemoteException {
  String info = System.getProperty("java.version");
  System.out.println("\nZdalne wywolanie metody getJVMInfo() klasy InfoActivatable");
  return info;
 } // getJVMInfo
 
} // InfoActivatable

