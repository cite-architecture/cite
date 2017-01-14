package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnConstructObject {

  // Notional collection, no version, object, not range, no extendedref
  @Test
  void testConstructor1() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:va012rn_0013")
   assert versObj.asString == "urn:cite2:hmt:vaimg:va012rn_0013"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == null
   assert versObj.collectionComponent == "vaimg"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == "va012rn_0013"
   assert versObj.objectId == "va012rn_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
  }

}
