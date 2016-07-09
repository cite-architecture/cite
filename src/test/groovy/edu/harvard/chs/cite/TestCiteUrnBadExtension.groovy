package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnBadExtension {



  // Extensions only allowed on
	// version-level URNs
  @Test
  void testBadExtension() {
		assert shouldFail {
				CiteUrn u = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1,0.1,0.2,.0.2")
		}
  }

}
