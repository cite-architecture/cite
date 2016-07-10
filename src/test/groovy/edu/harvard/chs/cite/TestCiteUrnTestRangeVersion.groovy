package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructRangeVersion {


  // Range Object, two versions, no extendedRef
  @Test
  void testConstructor() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013.v1-VA024RN_0025.v1"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "VA012RN_0013"
   assert versObj.objectId_2 == "VA024RN_0025"
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == "v1"
   assert versObj.objectVersion_2 == "v1"
  }
}
