package vsp.banks.helper;

/**
 * Created by alex on 11/24/15.
 */
public final class StringHelper {

  private StringHelper() {
    // prohibit instance
  }

  /**
   * Ensures the given strings aren't empty.
   */
  public static void checkNotEmpty(String ... strings) {
    String exceptionMsg = "String is empty, didn't expect that.";
    for (String string : strings) {
      if (string.isEmpty()) {
        throw new IllegalArgumentException(exceptionMsg);
      }
    }
  }
}
