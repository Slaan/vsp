package vsp.banks.business.logic.twophasecommit.interfaces;

import java.util.Set;

/**
 * Created by alex on 1/19/16.
 */
public interface IRegisterServices {

  /**
   * Notifies all services to remove given service.
   * @param uri of service to remove.
   * @return true, if and only if all services removed the service.
   */
  boolean deleteCloneService(String uri);

  /**
   * Registers all given uris to this service.
   * @param uri to register.
   * @return true, if and only if there is at least one uri which has been added.
   */
  boolean registerCloneServices(String uri);

  /**
   * Returns all registered uris.
   */
  Set<String> getUris();
}
