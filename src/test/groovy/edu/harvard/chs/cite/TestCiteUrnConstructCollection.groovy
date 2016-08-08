package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructCollection {




  // Collection only
  @Test
  void testConstructor() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg")
   assert versObj.asString == "urn:cite:hmt:vaimg"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg"
   assert versObj.objectId == null
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == null
   assert versObj.objectVersion_2 == null
  }

}
