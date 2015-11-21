package vsp.banks;

import vsp.banks.core.BanksLogic;

public class MyMainClass {

  public static void main(String[] args) {
    BanksLogic serviceLogic = new BanksLogic();
    BanksRestApi banksController = new BanksRestApi(serviceLogic);
  }
}
