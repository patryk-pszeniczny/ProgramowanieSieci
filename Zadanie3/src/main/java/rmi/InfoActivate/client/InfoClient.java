/*
 *  Koszalin 2003
 *  InfoClient.java
 *  Klient wywolujacy zdalna metode zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */
 
package rmi.InfoActivate.client;

import java.rmi.*;
import rmi.InfoActivate.serwer.InfoInterface;

public class InfoClient {

 public static void main(String[] args) {

  /* ustawienie zarzadcy ochrony */
  System.setSecurityManager(new RMISecurityManager());
  try {
  
    /* utworzenie obiektu zdalnego */
   InfoInterface robject = (InfoInterface) Naming.lookup("rmi://62.108.183.137:1099/InfoObject");

   System.out.println(" ------------ Info pobrane z obiektu ----"); 
    /* wywolanie metod zdalnych */
   String date = robject.getDate();
   System.out.println("Data na zdalnym hoscie: " + date );
   String os = robject.getOSInfo();
   System.out.println("  OS na zdalnym hoscie: " + os );
   String jvm = robject.getJVMInfo();
   System.out.println("  VM na zdalnym hoscie: " + jvm );

   System.out.println(" ----------------------------------------"); 
  } catch (Exception ex) { 
     System.err.println("Blad wywolania obiektu: " + ex); 
    }
 } // main
 
} // InfoClient   



