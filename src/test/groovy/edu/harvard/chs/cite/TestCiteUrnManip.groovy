package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManip {

  @Test
  void testObjectManipulation() {
    CiteUrn versionImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225")
    assert versionImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"

    CiteUrn objectImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225")
    assert objectImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
  }

  @Test
  void testRangeManipulation() {
    CiteUrn mixedLevels = new CiteUrn("urn:cite:examples:bifolios.1.v1-3.v1")
    assert mixedLevels.reduceToObject() == "urn:cite:examples:bifolios.1.v1-3.v1"
  }

  @Test
  void testGetCollection1(){
    CiteUrn urn = new CiteUrn("urn:cite:examples:bifolios.1.v1-3.v1")
		assert urn.getCollection() == "bifolios"
  }

  @Test
  void testGetCollection2(){
   CiteUrn urn = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
		assert urn.getCollection() == "vaimg"
  }
  @Test
  void testGetCollection3(){
   CiteUrn urn = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
		assert urn.getCollection() == "vaimg"
  }
  @Test
  void testGetCollection4(){
   CiteUrn urn = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@123,123,123,123")
		assert urn.getCollection() == "vaimg"
  }
  @Test
  void testGetCollection5(){
   CiteUrn urn = new CiteUrn("urn:cite:hmt:vaimg.VA0012RN_0013@0.12,0.12,0.12,0.12-VA024RN_0025")
		assert urn.getCollection() == "vaimg"
  }

  // getExtendedRef()
  // getFirstExtended()
  // getFirstObject()
  // getNs()
  // getObjectComponent()
  // getObjectId()
  // getObjectVersion()
  // getSecondExtended()
  // getSecondObject()
  // hasExtendedRef()
  // hasObjectId()
  // hasVersion()
  // isRange()
  // reduceToCollection()
  // reduceToObject()
  // getRangeBegin()

  @Test
  void testGetRangeBegin(){

   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

	assert notRange1.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013"
	assert notRange2.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
	assert notRange3.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12"
	assert range1.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
	assert range2.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013"
	assert range3.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225"
	assert range4.getRangeBegin() == "urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225"

  }

  // getRangeEnd()
	
  @Test
  void testGetRangeEnd(){

   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

	assert notRange1.getRangeEnd() == "urn:cite:hmt:vaimg.VA012RN_0013"
	assert notRange2.getRangeEnd() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
	assert notRange3.getRangeEnd() == "urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12"
	assert notRange1.getRangeEnd() == "urn:cite:hmt:vaimg.VA012RN_0013"
	assert notRange2.getRangeEnd() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
	assert notRange3.getRangeEnd() == "urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12"
	assert range1.getRangeEnd() == "urn:cite:hmt:vaimg.VA024RN_0025"
	assert range2.getRangeEnd() == "urn:cite:hmt:vaimg.VA024RN_0025@0.1532,0.1021,0.4014,0.0225"
	assert range3.getRangeEnd() == "urn:cite:hmt:vaimg.VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225"

  }


}
