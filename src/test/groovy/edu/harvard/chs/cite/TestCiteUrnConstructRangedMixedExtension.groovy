package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructRangeMixedExtension {

   // Range Object, 2 versions, extendedRef-noExtendedRef
  @Test
  void testConstructor16() {
    def msg = shouldFail {
      CiteUrn badObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v2")
    }
    System.err.println msg.getClass()
    assert msg.getMessage() == "Bad syntax in range. Both ends must identify the same version: #VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v2#"


    CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v1")
     
    assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v1"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == "0.1,0.1,0.1,0.1"
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025.v1"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "VA012RN_0013"
   assert versObj.objectId_2 == "VA024RN_0025"
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == "v1"
   assert versObj.objectVersion_2 == "v1"

  }

}
