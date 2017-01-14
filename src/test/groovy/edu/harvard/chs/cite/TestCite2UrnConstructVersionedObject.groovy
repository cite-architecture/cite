package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnConstructVersionedObject {

  // Notional collection, no version, object, not range, no extendedref
  @Test
  void testConstructor1() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:va012rn_0013")
   assert versObj.asString == "urn:cite2:hmt:vaimg.rel1:va012rn_0013"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "rel1"
   assert versObj.collectionComponent == "vaimg.rel1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == "va012rn_0013"
   assert versObj.objectId == "va012rn_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
  }

  @Test
  void testConstructor2() {
   Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:")
   assert versObj.asString == "urn:cite2:hmt:vaimg.rel1:"
   assert versObj.collection == "vaimg"
   assert versObj.collectionVersion == "rel1"
   assert versObj.collectionComponent == "vaimg.rel1"
   assert versObj.ns == "hmt"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.objectComponent == null
   assert versObj.objectId == null
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
  }
}
