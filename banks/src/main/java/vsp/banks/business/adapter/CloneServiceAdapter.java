package vsp.banks.business.adapter;

import vsp.banks.business.adapter.interfaces.ICloneServiceAdapter;
import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 1/17/16.
 */
public class CloneServiceAdapter implements ICloneServiceAdapter {

  public boolean lockBank(String uri, String bankId) {

    return false;
  }

  public boolean unlockBank(String uri, String bankId) {
    return false;
  }

  public boolean applyTransfer(String uri, String bankId, Transfer transfer) {
    return false;
  }

  public boolean lockPlayer(String uri, String bankId, Player playerId) {
    return false;
  }

  public boolean registerCloneService(String uri) {
    return false;
  }
}
