package vsp.banks.access;

import com.google.gson.Gson;

import static spark.Spark.*;
import static vsp.banks.data.values.StatusCodes.*;
/**
 * Created by alex on 1/17/16.
 */
abstract class AbstractFacade {

  protected Gson jsonConverter;

  public AbstractFacade() {
    this.jsonConverter = new Gson();
  }

  /**
   * Binds all REST api calls.
   */
  protected abstract void bindAllMethods();

}
