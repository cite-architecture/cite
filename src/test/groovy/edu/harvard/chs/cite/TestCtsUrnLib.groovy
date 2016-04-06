
package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Tests demonstrating various methods on Cts Urns.
*/
class TestCtsUrnLib {

  @Test
  void testGetPassage() {
    CtsUrn urn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:2")
	assert urn.getPassageNode() == "2"


  }



}
