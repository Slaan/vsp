package vsp.banks.business.adapter.interfaces;

import vsp.banks.business.logic.bank.interfaces.IBankLock;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicLockableMutable;
import vsp.banks.business.logic.bank.interfaces.IBanksLogicMutable;

/**
 * Created by alex on 1/17/16.
 */
public interface ICloneService extends IBanksLogicLockableMutable {

  /**
   * Get uri of clone service.
   * @return uri of service.
   */
  String getUri();
}
