package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnComponents {

	/*
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn noVersObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025.v1")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range5 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range6 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range7 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range8 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   */


  // Object, version, not range, no extendedRef
  @Test
  void testConstructor1() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013.v1"
   assert versObj.objectId == "VA012RN_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
   assert versObj.objectVersion == "v1"
   assert versObj.objectVersion_1 == null
   assert versObj.objectVersion_2 == null
  }

  // Object, no version, not range, no extendedRef
  @Test
  void testConstructor2() {
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

  // Object, no version, not range, extendedRef
  @Test
  void testConstructor3() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@123,123,123,123")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013@123,123,123,123"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == "123,123,123,123"
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013@123,123,123,123"
   assert versObj.objectId == "VA012RN_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == null
   assert versObj.objectVersion_2 == null
  }

  // Object, version, not range, extendedRef
  @Test
  void testConstructor4() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@123,123,123,123")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013.v1@123,123,123,123"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == "123,123,123,123"
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013.v1@123,123,123,123"
   assert versObj.objectId == "VA012RN_0013"
   assert versObj.objectId_1 == null
   assert versObj.objectId_2 == null
   assert versObj.objectVersion == "v1"
   assert versObj.objectVersion_1 == null
   assert versObj.objectVersion_2 == null
  }

  // Collection only
  @Test
  void testConstructor5() {
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

  // Range Object, no version, no extendedRef
  @Test
  void testConstructor6() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == null
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013-VA024RN_0025"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "VA012RN_0013"
   assert versObj.objectId_2 == "VA024RN_0025"
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == null
   assert versObj.objectVersion_2 == null
  }

}
