package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCiteUrnConstructBadRangeExtensions {

  // Range Object, noVersion-version, extendedRef-noExtendedRef
  @Test
  void testConstructorError() {
    assert shouldFail {
      CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1,0.1,0.1,0.1-VA024RN_0025.v1")
    }
  }

  // But this is OK: 
  // Range Object, version-Noversions, extendedRef-noExtendedRef
  @Test
  void testConstructor1() {

   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1,0.1,0.1,0.1-VA024RN_0025")
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


  // as is this:
  @Test
  void testConstructor2() {
   CiteUrn versObj = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025.v1@0.2,0.2,0.2,0.2")
   assert versObj.asString == "urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1@0.2,0.2,0.2,0.2"
   assert versObj.collection == "vaimg"
   assert versObj.extendedRef == null
   assert versObj.extendedRef_1 == null
   assert versObj.extendedRef_2 == "0.2,0.2,0.2,0.2"
   assert versObj.ns == "hmt"
   assert versObj.objectComponent == "vaimg.VA012RN_0013.v1-VA024RN_0025.v1@0.2,0.2,0.2,0.2"
   assert versObj.objectId == null
   assert versObj.objectId_1 == "VA012RN_0013"
   assert versObj.objectId_2 == "VA024RN_0025"
   assert versObj.objectVersion == null
   assert versObj.objectVersion_1 == "v1"
   assert versObj.objectVersion_2 == "v1"
  }

}
