package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructVersNoVersExtended {


   // Range Object, version-Noversions, 2 extendedRef
  @Test
  void testConstructorVersNoVers() {
    assert shouldFail {
      CiteUrn badObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025@0.2,0.2,0.2,0.2")
    }
  }

  
   // Range Object, noVersion-version, 2 extendedRef
  @Test
  void testConstructorNoVersThenVers() {
    assert shouldFail {
      CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1,0.1,0.1,0.1-VA024RN_0025.v1@0.2,0.2,0.2,0.2")
    }
  }


}
