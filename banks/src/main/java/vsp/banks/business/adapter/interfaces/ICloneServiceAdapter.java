package vsp.banks.business.adapter.interfaces;

import vsp.banks.data.entities.Player;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 1/17/16.
 */
public interface ICloneServiceAdapter {

  /**
   *
   * @param bankId
   * @return
   */
  boolean lockBank(String uri, String bankId);

  /**
   *
   * @param bankId
   * @return
   */
  boolean unlockBank(String uri, String bankId);

  /**
   *
   * @param bankId
   * @param transfer
   * @return
   */
  boolean applyTransfer(String uri, String bankId, Transfer transfer);

  /**
   *
   * @param bankId
   * @param playerId
   * @return
   */
  boolean lockPlayer(String uri, String bankId, Player playerId);

  /**
   *
   * @param uri
   * @return
   */
  boolean registerCloneService(String uri);
}
