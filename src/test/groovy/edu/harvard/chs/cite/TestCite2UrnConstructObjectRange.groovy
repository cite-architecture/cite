package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnConstructObjectRange {

  // Notional collection, no version, object,range, no extendedref
  @Test
  void testConstructor1() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:va012rn_0013-va012v_0014")
   assert versObj.asString == "urn:cite2:hmt:vaimg:va012rn_0013-va012v_0014"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == null
   assert versObj.collectionComponent == "vaimg"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == "va012rn_0013-va012v_0014"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "va012rn_0013"
   assert versObj.objectId_2 == "va012v_0014"
  }

  // Versioned collection, object,range, no extendedref
  @Test
  void testConstructor2() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.release1:va012rn_0013-va012v_0014")
   assert versObj.asString == "urn:cite2:hmt:vaimg.release1:va012rn_0013-va012v_0014"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "release1"
   assert versObj.collectionComponent == "vaimg.release1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == "va012rn_0013-va012v_0014"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "va012rn_0013"
   assert versObj.objectId_2 == "va012v_0014"
  }

	// Bad range, too many hyphens
  @Test
  void testConstructor3() {
		assert shouldFail{
	   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.release1:va012rn_0013-va012v_0014-va13r_0015")
	 }
 }

}
