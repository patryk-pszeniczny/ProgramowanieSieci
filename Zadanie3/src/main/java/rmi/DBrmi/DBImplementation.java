package rmi.DBrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

public class DBImplementation extends UnicastRemoteObject implements DBInterface {

  private static final String DRIVER = "org.hsqldb.jdbcDriver";
  private static final String URL = "jdbc:hsqldb:hsql://localhost:9001/shopdb";
  private static final String USER = "student";
  private static final String PASSWORD = "student";

  private Connection connect;
  private Statement stmt;

  public DBImplementation() throws RemoteException {
    super();
  }

  @Override
  public synchronized void connectDatabase() throws RemoteException {
    if (connect != null) {
      return;
    }

    try {
      Class.forName(DRIVER);
      connect = DriverManager.getConnection(URL, USER, PASSWORD);
      stmt = connect.createStatement();
      System.out.println("Polaczono z baza: " + URL);
    } catch (Exception e) {
      throw new RemoteException("Problem z polaczeniem do " + URL + ": " + e.getMessage(), e);
    }
  }

  @Override
  public synchronized void disconnectDatabase() throws RemoteException {
    try {
      if (stmt != null) {
        stmt.close();
      }
      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {
      throw new RemoteException("Problem z rozlaczeniem: " + e.getMessage(), e);
    } finally {
      stmt = null;
      connect = null;
    }
  }

  private void ensureConnection() throws RemoteException {
    if (connect == null || stmt == null) {
      connectDatabase();
    }
  }

  @Override
  public synchronized int getColumnCount(String table) throws RemoteException {
    ensureConnection();
    try (ResultSet rs = stmt.executeQuery("select * from " + table)) {
      ResultSetMetaData md = rs.getMetaData();
      return md.getColumnCount();
    } catch (Exception e) {
      throw new RemoteException("Problem z odczytem kolumn tabeli " + table + ": " + e.getMessage(), e);
    }
  }

  @Override
  public synchronized Vector getColumns(String table) throws RemoteException {
    ensureConnection();
    Vector columns = new Vector();

    try (ResultSet rs = stmt.executeQuery("select * from " + table)) {
      ResultSetMetaData md = rs.getMetaData();
      int ncols = md.getColumnCount();
      for (int i = 1; i <= ncols; i++) {
        columns.addElement(md.getColumnName(i));
      }
    } catch (Exception e) {
      throw new RemoteException("Problem z odczytem nazw kolumn: " + e.getMessage(), e);
    }

    return columns;
  }

  @Override
  public synchronized Vector getTableData(String table) throws RemoteException {
    ensureConnection();
    Vector data = new Vector();

    try (ResultSet rs = stmt.executeQuery("select * from " + table)) {
      ResultSetMetaData md = rs.getMetaData();
      int ncols = md.getColumnCount();
      while (rs.next()) {
        for (int i = 1; i <= ncols; i++) {
          data.addElement(rs.getString(i));
        }
      }
    } catch (Exception e) {
      throw new RemoteException("Problem z odczytem danych tabeli " + table + ": " + e.getMessage(), e);
    }

    return data;
  }
}
