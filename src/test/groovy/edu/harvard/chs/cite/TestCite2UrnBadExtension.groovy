package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnBadExtension {



  // Extensions only allowed on
	// version-level URNs
  @Test
  void testBadExtensionNode() {
		assert shouldFail {
				Cite2Urn u1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013@0.1,0.1,0.2,.0.2")
		}
    // and make sure a good one works:
    Cite2Urn u2 = new Cite2Urn("urn:cite2:hmt:vaimg.release1:VA012RN_00131@0.1,0.1,0.2,.0.2")
    assert u2
  }
  /*
  @Test
  void testBadExtensionRange() {
    assert shouldFail {
      CiteUrn urn = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@12,12,12,12-VA024RN_0025@11,11,11,11")
    }
    // and test a good one on version level:
    CiteUrn u2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@12,12,12,12-VA024RN_0025.v1@11,11,11,11")
  }
*/

}
