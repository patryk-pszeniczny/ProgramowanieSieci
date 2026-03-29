/*
 *  Koszalin 2003
 *  RMIRegistryList.java
 *  Aplikacja pobiera i usuwa obiekty 
 *  zarejestrowane w RMI Registry
 *  Dariusz Rataj (C)
 */

package rmi.RMIRegistryList;

import java.io.*;
import java.rmi.*;

public class RMIRegistryList {

 String url = "rmi://localhost";

public static void printMenu() {
 System.out.println("Wybor z MENU");
 System.out.println(" [1] - Wyswietl obiekty RMI Registry");
 System.out.println(" [2] - Usun obiekty RMI Registry");
 System.out.println(" [0] - Wyjscie");
}

/* usuniecie zarejestrowanych obiektow */
public void deleteObjects(){
 try {
   System.out.println(" -------- Usuwam obiekty -----------------------------");
   String[] rObjects = Naming.list(url);
   for (int i = 0; i < rObjects.length; i++) {
     try {
      Naming.unbind(rObjects[i]); 
      System.out.println(" Obiekt " + rObjects[i] + " usuniety");
     }
      catch (NotBoundException e) {
         System.err.println("Obiekt nie zwiazany!");
      }
      catch (ServerException e) {
         System.err.println("unbind () - Brak dostepu do obiektu! Dostęp tylko lokalny.");
      }

   }
 }
 catch (Exception e) {
   System.err.println(e);
 }
 System.out.println(" ---------------------------------------------------");
}

/* pobranie listy zarejestrowanych obiektow */
public void listObjects(){
 System.out.println(" -------- Lista obiektow -----------------------------");
 try {
   String[] rObjects = Naming.list(url);
   for (int i = 0; i < rObjects.length; i++) {
     System.out.println(rObjects[i]);
   }
 }
 catch (Exception e) {
   System.err.println(e);
 }
 System.out.println(" ---------------------------------------------------");
}

public static void main(String[] args) throws IOException {
  RMIRegistryList lister = new RMIRegistryList(); 
  while (true) {
  RMIRegistryList.printMenu();
  int option = 0;  // odczyt jednego znaku
  while (option != '0' && option != '1' && option != '2')  option = System.in.read();
	   switch (option)
	    {
	     case '1':	lister.listObjects(); break;
	     case '2':	lister.deleteObjects(); break;
	     case '0':	System.exit(0); break;
	    } // koniec switch
  } // koniec while (true)
 } // koniec main
 
} // koniec RMIRegistryList
