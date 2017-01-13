package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnConstructObjectRangeWExtension {

  // Range, 2 extensions
  @Test
  void testConstructor1() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:va012rn_0013@12,12,12,12-va012v_0014@13,13,13,13")
   assert versObj.asString == "urn:cite2:hmt:vaimg.rel1:va012rn_0013@12,12,12,12-va012v_0014@13,13,13,13"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "rel1"
   assert versObj.collectionComponent == "vaimg.rel1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == "12,12,12,12"
   assert versObj.extendedRef_2 == "13,13,13,13"
   assert versObj.objectComponent == "va012rn_0013@12,12,12,12-va012v_0014@13,13,13,13"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "va012rn_0013"
   assert versObj.objectId_2 == "va012v_0014"
  }

  // Range, extensions, no-extension
  @Test
  void testConstructor2() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:va012rn_0013@12,12,12,12-va012v_0014")
   assert versObj.asString == "urn:cite2:hmt:vaimg.rel1:va012rn_0013@12,12,12,12-va012v_0014"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "rel1"
   assert versObj.collectionComponent == "vaimg.rel1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == "12,12,12,12"
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == "va012rn_0013@12,12,12,12-va012v_0014"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "va012rn_0013"
   assert versObj.objectId_2 == "va012v_0014"
  }

  // Range, no-extension, extension
  @Test
  void testConstructor3() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:va012rn_0013-va012v_0014@13,13,13,13")
   assert versObj.asString == "urn:cite2:hmt:vaimg.rel1:va012rn_0013-va012v_0014@13,13,13,13"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "rel1"
   assert versObj.collectionComponent == "vaimg.rel1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == "13,13,13,13"
   assert versObj.objectComponent == "va012rn_0013-va012v_0014@13,13,13,13"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "va012rn_0013"
   assert versObj.objectId_2 == "va012v_0014"
  }

	// Invalid, due to lack of version #1
	@Test
	void testConstructor4() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:va012rn_0013@12,12,12,12-va012v_0014@13,13,13,13")
		}
	}

	// Invalid, due to lack of version #2
	@Test
	void testConstructor5() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:va012rn_0013@12,12,12,12-va012v_0014")
		}
	}

	// Invalid, due to lack of version #3
	@Test
	void testConstructor6() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:va012rn_0013-va012v_0014@13,13,13,13")
		}
	}
}
