package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructObject {




  // Object, no version, not range, no extendedRef
  @Test
  void testConstructor() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013"
   assert versObj.objectId == "VA012RN_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == null
   assert versObj.objectVersion_2 == null
  }
}
