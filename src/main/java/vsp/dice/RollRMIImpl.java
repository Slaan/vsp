package vsp.dice;

import java.rmi.RemoteException;
import java.util.Random;

/**
 * Created by alex on 10/24/15.
 */
public class RollRMIImpl implements RollRMI {

  @Override public Roll roll() throws RemoteException {
    Random random = new Random();
    return new Roll(random.nextInt(6) + 1);
  }
}
