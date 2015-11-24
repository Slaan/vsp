package vsp.banks.helper;

/**
 * Created by alex on 11/20/15.
 */
public final class ObjectHelper {

  private ObjectHelper() {

  }

  /**
   * Ensures all given arguments aren't null.
   * When one is null, an IllegalArgumentException will be thrown.
   */
  public static void checkNotNull(Object firstObject, Object ... objects) {
    // separate firstObject from array, so we force at lease one object.
    if (firstObject == null) {
      throw new IllegalArgumentException("Given argument can't be null.");
    }
    for (Object object : objects) {
      if (object == null) {
        throw new IllegalArgumentException("Given argument can't be null.");
      }
    }
  }

}
