package vsp.dice;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by alex on 10/24/15.
 */
public interface RollRMI extends Remote {
  Roll roll() throws RemoteException;
}
