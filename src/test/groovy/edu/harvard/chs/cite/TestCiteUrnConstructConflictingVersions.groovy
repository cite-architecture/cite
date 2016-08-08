package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructConflictingVersions {


  // Range Object, 2 versions, 2 extendedRef
  // Should fail: you can't have two different versions
  @Test
  void testConstructor() {
    def msg = shouldFail {
      CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v2@0.2,0.2,0.2,0.2")
    }
    System.err.println msg.getClass()
    assert msg.getMessage() == "Bad syntax in range. Both ends must identify the same version: #VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v2@0.2,0.2,0.2,0.2#"
  }
}
