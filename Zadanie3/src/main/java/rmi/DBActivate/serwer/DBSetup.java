/*
 *  Koszalin 2003
 *  DBSetup.java
 *  Implementacja klasy zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */

import java.rmi.*;
import java.rmi.activation.*;
import java.util.*;

public class DBSetup {

// DBSetup() {}
 public static void main(String[] args){

   System.setSecurityManager(new RMISecurityManager());
   Properties props = System.getProperties();
   
   try {
    // utworzenie deskryptora grupy obiektow
    ActivationGroupDesc.CommandEnvironment ace = null; 
    ActivationGroupDesc group = new ActivationGroupDesc(props, ace); 
    
    // utworzenie grupy obiektow
    ActivationGroupID id = ActivationGroup.getSystem().registerGroup(group); 
    ActivationGroup.createGroup(id, group, 0);
    
    
    // lokalizacja namiastki
    String lokalizacja = "file:/d:/server/"; 
    
    // -------- obiekt 1 --------------
    // obiekt przekazujacy dane do aktywowanego obiektu - tutaj url do bazy danych
    MarshalledObject initdata = new MarshalledObject(new String("jdbc:odbc:BooksDB1")); 
    // utworzenie deskryptora obiektu do aktywacji
    ActivationDesc descriptor = new ActivationDesc("DBActivatable", lokalizacja, initdata); 
    // utworzenie interfejsu (powiazanie z deskryptorem)
    DBInterface interfejs = (DBInterface) Activatable.register(descriptor);
    // rejestracja obiektu w RMI Registry
    Naming.rebind("DBObject", interfejs);
    
    // -------- obiekt 2 --------------
    initdata = new MarshalledObject(new String("jdbc:odbc:BooksDB2")); 
    descriptor = new ActivationDesc("DBActivatable", lokalizacja, initdata); 
    interfejs = (DBInterface) Activatable.register(descriptor);
    Naming.rebind("DBObject2", interfejs);

 
    System.out.println("Obiekty DBObject i DBObject2 gotowe... ");
    System.exit(0);
   }
    catch (Exception ex) {
     System.out.println("Blad aktywacji obiektu! ");
     ex.printStackTrace();
    }
}



 
 }
