/*
 *  Koszalin 2002
 *  DateServer.java
 *  Serwer udostepniajacy zdalny obiekt RMI
 *  Dariusz Rataj (C)
 */
 
import java.rmi.*;
import java.rmi.server.*;

public class DateServer {

 public static void main(String[] args) {
 
   System.setSecurityManager(new RMISecurityManager());
   
   try {
   
    /* utworzenie instancji zdalnego obiektu */
    DateImplementation robject = new DateImplementation();
    
    /* wystawienie (rejestracja) obiektu */
    Naming.rebind("rmi://localhost/DateObject", robject);
    
    System.out.println("Obiekt DateObject przygotowany");
   } catch (Exception ex)  { 
      System.out.println("Blad aktywacji obiektu" + ex); 
     }
 }
 
} // DateServer 
