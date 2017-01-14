package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnConstructObjectWithExtension {

  // Notional collection, no version, object, not range, no extendedref
  @Test
  void testConstructor1() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.v1:va012rn_0013@12,12,12,12")
   assert versObj.asString == "urn:cite2:hmt:vaimg.v1:va012rn_0013@12,12,12,12"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "v1"
   assert versObj.collectionComponent == "vaimg.v1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == "12,12,12,12"
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == "va012rn_0013@12,12,12,12"
   assert versObj.objectId == "va012rn_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
  }

	@Test
  void testConstructor2() {
			// Should fail, since there is no collection-version
		assert shouldFail{
		   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:va012rn_0013@12,12,12,12")
		  }
 }


}
