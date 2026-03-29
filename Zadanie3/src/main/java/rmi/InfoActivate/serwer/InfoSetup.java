/*
 *  Koszalin 2003
 *  InfoSetup.java
 *  Implementacja klasy zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */

import java.rmi.*;
import java.rmi.activation.*;
import java.util.*;

public class InfoSetup {

 InfoSetup() {}
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
    // obiekt przekazujacy dane do aktywowanego obiektu - tutaj null
    MarshalledObject initdata = null; 

    // utworzenie deskryptora obiektu do aktywacji
    ActivationDesc descriptor = new ActivationDesc("InfoActivatable", lokalizacja, initdata); 
    // utworzenie interfejsu (powiazanie z deskryptorem)
    InfoInterface interfejs = (InfoInterface) Activatable.register(descriptor);
    // rejestracja obiektu w RMI Registry
    Naming.rebind("InfoObject", interfejs);
 
    System.out.println("Obiekt InfoObject gotowy... ");
    System.exit(0);
   }
    catch (Exception ex) {
     System.out.println("Blad aktywacji obiektu! ");
     ex.printStackTrace();
    }
}



 
 }
