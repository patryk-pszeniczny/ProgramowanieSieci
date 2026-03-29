/*
 *  Koszalin 2002
 *  DateImplementation.java
 *  Implementacja klasy zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */
 
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class DateImplementation extends UnicastRemoteObject implements DateInterface {

 
 public DateImplementation() throws RemoteException {
   super(); // wywo³anie konstruktora klasy UnicastRemoteObject
 }

 /* implementacja metody zdalnej */
 public String getDate() throws RemoteException {

  Date date = new Date();
  System.out.println("Zdalne wywolanie metody getDate() klasy DateImplementation");
  return date.toString();
 } // getDate
  
} // DateImplementation
