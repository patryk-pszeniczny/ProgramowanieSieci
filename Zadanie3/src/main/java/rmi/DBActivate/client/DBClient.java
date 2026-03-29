/*
 *  Koszalin 2003
 *  DBClient.java
 *  Klient wywolujacy zdalna metode zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */
 
import java.rmi.*;
import java.util.*;
import java.sql.*;

public class DBClient {

 public static void main(String[] args) {
 
  String table = "books";
  
  /* ustawienie zarzadcy ochrony */
  System.setSecurityManager(new RMISecurityManager());
  try {
  
    /* utworzenie obiektu zdalnego */
   DBInterface robject = (DBInterface) Naming.lookup("rmi://127.0.0.1:1099/DBObject2");

    /* wywolanie metody zdalnej */
   /* wywolanie metody zdalnej - polaczenie z baza danych */
   robject.connectDatabase();
   
   /* wywolanie metody zdalnej - pobranie ilosci kolumn */
   int colcount = robject.getColumnCount(table);
   System.out.println("\n\r --------------- Dane tabeli " + table + "-------------------- ");
   
   /* wywolanie metody zdalnej - pobranie nazw kolumn */
   Vector columns = robject.getColumns(table);
   /* wywolanie metody zdalnej - pobranie danych z tabeli */
   Vector data = robject.getTableData(table);
   int rowcount = data.size()/colcount;
   System.out.println("Cols: " + colcount + " Rows:"+ rowcount);

   /* wyswietlenie nazw kolumn tabeli */
   for (int i = 0 ; i < columns.size(); i++) {
     System.out.print("| " + (i+1) + "." + columns.elementAt(i) + "\t");
   }
   System.out.println("\n -------------------------------------------------------");
   /* wyswietlenie danych tabeli */
   for (int i = 0 ; i < rowcount; i++) {
   for (int j = 0 ; j < colcount; j++) {
     System.out.print("| " + data.elementAt(i*colcount+j) + "\t");
    }
    System.out.print("\n");
   } 
   /* wywolanie metody zdalnej - zakniecie polaczenia z baza danych */
   robject.disconnectDatabase();


   System.out.println(" ----------------------------------------"); 
  } catch (Exception ex) { 
     System.err.println("Blad wywolania obiektu: " + ex); 
    }
 } // main
 
} // InfoClient   


