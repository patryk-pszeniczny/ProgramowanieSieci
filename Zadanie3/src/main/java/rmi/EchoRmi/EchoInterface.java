package rmi.EchoRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoInterface extends Remote {
  String echo(String message) throws RemoteException;
}
