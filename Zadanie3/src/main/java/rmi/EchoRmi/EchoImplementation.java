package rmi.EchoRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EchoImplementation extends UnicastRemoteObject implements EchoInterface {

  public EchoImplementation() throws RemoteException {
    super();
  }

  @Override
  public String echo(String message) throws RemoteException {
    System.out.println("Wywolanie echo(), wiadomosc: " + message);
    return message;
  }
}
