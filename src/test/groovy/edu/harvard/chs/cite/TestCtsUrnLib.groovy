
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

  @Test
  void testCanWeHaveHyphens() {
    CtsUrn urn = new CtsUrn("urn:cts:greekLit:tlg-0012.tlg-001.msA:2")
	assert urn
	
    CtsUrn toomany = new CtsUrn("urn:cts:greekLit:tlg-0012.tlg-001.msA:2-3-4")

	assert toomany



  }


}
