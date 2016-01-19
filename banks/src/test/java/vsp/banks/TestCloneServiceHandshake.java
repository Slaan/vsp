package vsp.banks;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by alex on 1/19/16.
 */
@Test
public class TestCloneServiceHandshake {

  /*
   * Handshake between replicates.
   * client to new replicate B: "Ask replicate A about other replicates".
   *
   *   Replicate A: new replicate.
   *   Replicate B: old replicate.
   *
   * Replicate A                                   Replicate B
   *      +                                             +
   *      |                                             |
   *      |      Hey mate, know some other replicates?  |  POST /replicate/uris
   *      |     btw, my uri is: <myUri>                 |    body: uri of A without http, with port
   *      | +-----------------------------------------> |   response:
   *      |                                             |    body: list of known replicate uris
   *      |                                             |
   *      |                                             |
   *      |     Of couse, here we go: set of replicates |
   *      | <----------------------------------------+  |
   *      |                                             |
   *      |                                             |
   *      |                                             |
   *      | +----+  Says hello to other                 |
   *      |      |  replicates.                          |
   *      | <----+                                      |
   *      |                                             |
   *      |                                             |
   *      |                                             |
   *      +                                             +
   *
   */


}
