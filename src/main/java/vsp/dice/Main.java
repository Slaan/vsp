package vsp.dice;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class Main {

  public static final String bindingName = "roll";

  private static boolean isInvoker;
  private static String hostAddress;

  /**
   * @param args are the arguments parsed by console.
   * @return true when successfully parsed. Else false.
   */
  private static boolean parseArgs(String[] args) {
    if (args.length == 0) {
      Main.isInvoker = false;
      return true;
    }
    if (args.length == 1) {
      Main.hostAddress = args[0];
      Main.isInvoker = true;
      return true;
    }
    System.err.println("Reason: Invalid amount of arguments!\n");
    return false;
  }

  /**
   * Registers a Roll on this computer at port 'Main.port' for RMI.
   */
  private static void registerRoll() {
    try {
      LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
      Naming.rebind(bindingName, new RollRMIImpl());
    } catch (ExportException e) {
      System.err.println("Couldn't bind roll service.\n");
      System.err.println("Already other serivce bound?\n");
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets and invokes a remote Roll.
   */
  private static void invokeRemoteRoll() {
    String methodPath = "//" + Main.hostAddress + "/" + bindingName;
    try {
      RollRMI rollRmi = (RollRMI) Naming.lookup(methodPath);
      Roll roll = rollRmi.roll();
      System.out.println("Rolled: " + roll.getNumber());
    } catch (NotBoundException e) {
      e.printStackTrace();
    } catch (java.rmi.ConnectException e) {
      System.err.println("An connection error happend...");
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  private static void printParsed() {
    System.out.println("Main.isInvoker: " + Main.isInvoker);
    if (Main.isInvoker) {
      System.out.println("Main.hostAddress: " + Main.hostAddress);
    }
  }

  /**
   * Main Method.
   * @param args passed by console.
   */
  public static void main(String[] args) {
    if (!parseArgs(args)) {
      String errMsg =
          "Execute dice server: \n" + "./dice\n" + "Register new dice to dice server:\n"
              + "./dice <host>";
      System.err.println(errMsg);
      System.exit(1);
    }
    printParsed();
    if (isInvoker) {
      // start client
      invokeRemoteRoll();
    } else {
      // start server
      registerRoll();
    }
  }
}
