/*
 *  Koszalin 2003
 *  DBActivatable.java
 *  Implementacja klasy zdalnego obiektu RMI
 *  Dariusz Rataj (C)
 */
package rmi.DBActivate.serwer;
import java.rmi.*;
import java.rmi.activation.*;
import java.util.*;
import java.sql.*;

public class DBActivatable extends Activatable implements DBInterface {

 String url = "jdbc:odbc:BooksDB";
 Connection connect = null;
 Statement stmt = null;
 
 public DBActivatable(ActivationID id, MarshalledObject data) throws RemoteException {
  super(id, 0); // wywoďż˝anie konstruktora klasy Activatable
  try {
   if (data != null)
   url = (String)data.get();
  }
  catch (Exception ex){
   System.out.println("Blad obiektu data!");   
  } 
 }

 public void connectDatabase() throws RemoteException {

  System.out.println("Zdalne wywolanie metody connectDatabase() obiektu klasy DBImplementation");   
  if (connect == null) {
   try 
    {
     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); // zaladowanie sterownika
    } 
    catch(java.lang.ClassNotFoundException e) 
    {
     System.err.println(e.getMessage());
    }
    try {
       connect = DriverManager.getConnection(url); // polaczenie z baza
       stmt = connect.createStatement();        // zainicjowanie Statement 
     } catch (Exception e) 
     {
        System.err.println("Problem z polaczeniem: "+url+ ": "+e.getMessage());
     } // catch    
  } // if
 } // ConnectDatabase

 public void disconnectDatabase() throws RemoteException {
 
  System.out.println("Zdalne wywolanie metody disconnectDatabase() obiektu klasy DBImplementation");   
  
  try {
       stmt.close();
       connect.close();
       connect = null;
   } catch (Exception e) {
      System.err.println("Problem z zakonczeniem polaczenia: "+url+ ": "+e.getMessage());
     } // catch
 } // disconnectDatabase

 public int getColumnCount(String table) throws RemoteException {
 
  int ncols;
  System.out.println("Zdalne wywolanie metody getColumnCount("+table+") obiektu klasy DBImplementation");
  
  try {
   ResultSet rs = stmt.executeQuery("select * from " + table);
   ResultSetMetaData md = rs.getMetaData();
   ncols = md.getColumnCount();
   }
  catch(Exception e) { e.printStackTrace(); return 0;}
  return ncols;
 } // getColumnCount

 public Vector getColumns(String table) throws RemoteException {

   Vector columns = new Vector();
   String cname;        
   
   System.out.println("Zdalne wywolanie metody getColumns("+table+") obiektu klasy DBImplementation");
   try {
       ResultSet rs = stmt.executeQuery("select * from " + table);
       ResultSetMetaData md = rs.getMetaData();
       int ncols = md.getColumnCount();
       columns.clear();
       for(int i = 1 ; i <= ncols; i++) { 
        cname = md.getColumnName(i);
        columns.addElement(cname);
       }
      } catch (Exception e) {
       System.err.println("Problem z poleceniem select SQL do "+url+ ": "+e.getMessage());
      }
  
  return columns;
 } // getColumns
 
 public Vector getTableData(String table) throws RemoteException {

   Vector data = new Vector();
   String text;        
   
   System.out.println("Zdalne wywolanie metody getData("+table+") obiektu klasy DBImplementation");
   try {
       ResultSet rs = stmt.executeQuery("select * from " + table);
       ResultSetMetaData md = rs.getMetaData();
       int ncols = md.getColumnCount();
       data.clear();
       while(rs.next())    // kolejny rekord            
       for(int i = 1 ; i <= ncols; i++) { 
        text = rs.getString(i);
        data.addElement(text);
       }
      } catch (Exception e) {
       System.err.println("Problem z poleceniem select SQL do "+url+ ": "+e.getMessage());
      }
  
  return data;
 } // getData
 
} // DBActivatable

