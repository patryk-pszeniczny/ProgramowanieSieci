package rmi.DBrmi;

import java.rmi.Naming;
import java.util.Vector;

public class DBClient {

  public static void main(String[] args) {
    String host = args.length > 0 ? args[0] : "127.0.0.1";
    String table = args.length > 1 ? args[1] : "customer_tbl";
    int port = 1099;

    try {
      DBInterface remoteObject =
          (DBInterface) Naming.lookup("rmi://" + host + ":" + port + "/DatabaseObject");

      remoteObject.connectDatabase();

      int colcount = remoteObject.getColumnCount(table);
      Vector columns = remoteObject.getColumns(table);
      Vector data = remoteObject.getTableData(table);

      if (colcount <= 0) {
        System.out.println("Brak kolumn do wyswietlenia dla tabeli: " + table);
        remoteObject.disconnectDatabase();
        return;
      }

      int rowcount = data.size() / colcount;
      System.out.println("--------------- Dane tabeli " + table + " --------------------");
      System.out.println("Cols: " + colcount + " Rows: " + rowcount);

      for (int i = 0; i < columns.size(); i++) {
        System.out.print("| " + (i + 1) + "." + columns.elementAt(i) + "\t");
      }
      System.out.println();
      System.out.println("---------------------------------------------------------------");

      for (int i = 0; i < rowcount; i++) {
        for (int j = 0; j < colcount; j++) {
          System.out.print("| " + data.elementAt(i * colcount + j) + "\t");
        }
        System.out.println();
      }

      remoteObject.disconnectDatabase();
      System.out.println("---------------------------------------------------------------");
    } catch (Exception ex) {
      System.out.println("Blad wywolania obiektu: " + ex);
      ex.printStackTrace();
    }
  }
}
