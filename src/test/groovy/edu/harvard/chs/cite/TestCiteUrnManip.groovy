package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManip {

  @Test
  void testObjectManipulation() {
    CiteUrn versionImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225")
    assert versionImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
    assert versionImgWRoi.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"

    CiteUrn objectImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225")
    assert objectImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
  }

  @Test
  void testRangeManipulation() {
    CiteUrn mixedLevels = new CiteUrn("urn:cite:examples:bifolios.1.v1-3.v1")
    assert mixedLevels.reduceToObject() == "urn:cite:examples:bifolios.1-3"
    assert mixedLevels.reduceToVersion() == "urn:cite:examples:bifolios.1.v1-3.v1"
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
  @Test
  void testGetExtendedRef(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getExtendedRef() == null
   assert notRange2.getExtendedRef() == null
   assert notRange3.getExtendedRef() == "0.12,0.12,0.12,0.12"
   assert notRange4.getExtendedRef() == "0.12,0.12,0.12,0.12"
   assert range1.getExtendedRef() == null
   assert range2.getExtendedRef() == null
   assert range3.getExtendedRef() == null
   assert range4.getExtendedRef() == null

  }
  // getFirstExtended()
  @Test
  void testGetFirstExtended(){
	  
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getFirstExtended() == null
   assert notRange2.getFirstExtended() == null
   assert notRange3.getFirstExtended() == null
   assert notRange4.getFirstExtended() == null
   assert range1.getFirstExtended() == null
   assert range2.getFirstExtended() == null
   assert range3.getFirstExtended() == "0.1532,0.1021,0.4014,0.0225"
   assert range4.getFirstExtended() == "0.1532,0.1021,0.4014,0.0225"

  }
  // getFirstObject()
  @Test
  void testGetFirstObject(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getFirstObject() ==  null
   assert notRange2.getFirstObject() ==  null
   assert notRange3.getFirstObject() ==  null
   assert notRange4.getFirstObject() ==  null
   assert range1.getFirstObject() == "VA012RN_0013"
   assert range2.getFirstObject() == "VA012RN_0013"
   assert range3.getFirstObject() == "VA012RN_0013"
   assert range4.getFirstObject() == "VA012RN_0013"

  }
  // getNs()
  @Test
  void testGetNs(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getNs() == "hmt"
   assert notRange2.getNs() == "hmt"
   assert notRange3.getNs() == "hmt"
   assert notRange4.getNs() == "hmt"
   assert    range1.getNs() == "hmt"
   assert    range2.getNs() == "hmt"
   assert    range3.getNs() == "hmt"
   assert    range4.getNs() == "hmt"

  }
  // getObjectComponent()
  @Test
  void testGetObjectComponent(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getObjectComponent() == "vaimg.VA012RN_0013"
   assert notRange2.getObjectComponent() == "vaimg.VA012RN_0013.v1"
   assert notRange3.getObjectComponent() == "vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12"
   assert notRange4.getObjectComponent() == "vaimg.VA012RN_0013@0.12,0.12,0.12,0.12"
   assert    range1.getObjectComponent() == "vaimg.VA012RN_0013.v1-VA024RN_0025"
   assert    range2.getObjectComponent() == "vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225"
   assert    range3.getObjectComponent() == "vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225"
   assert    range4.getObjectComponent() == "vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225"

  }
  // getObjectId()
  @Test
  void testGetObjectId(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getObjectId() == "VA012RN_0013"
   assert notRange2.getObjectId() == "VA012RN_0013"
   assert notRange3.getObjectId() == "VA012RN_0013"
   assert notRange4.getObjectId() == "VA012RN_0013"
   assert    range1.getObjectId() == null
   assert    range2.getObjectId() == null
   assert    range3.getObjectId() == null
   assert    range4.getObjectId() == null

  }
  // getObjectVersion()
  @Test
  void testGetObjectVersion(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getObjectVersion() == null
   assert notRange2.getObjectVersion() == "v1"
   assert notRange3.getObjectVersion() == "v1"
   assert notRange4.getObjectVersion() == null
   assert    range1.getObjectVersion() == null
   assert    range2.getObjectVersion() == null
   assert    range3.getObjectVersion() == null
   assert    range4.getObjectVersion() == null

  }
  // getSecondExtended()
  @Test
  void testGetSecondExtended(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getSecondExtended() == null
   assert notRange2.getSecondExtended() == null
   assert notRange3.getSecondExtended() == null
   assert notRange4.getSecondExtended() == null
   assert    range1.getSecondExtended() == null
   assert    range2.getSecondExtended() == "0.1532,0.1021,0.4014,0.0225"
   assert    range3.getSecondExtended() == "0.1532,0.1021,0.4014,0.0225"
   assert    range4.getSecondExtended() == "0.1532,0.1021,0.4014,0.0225"

  }
  // getSecondObject()
  @Test
  void testGetSecondObject(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.getSecondObject() == null
   assert notRange2.getSecondObject() == null
   assert notRange3.getSecondObject() == null
   assert notRange4.getSecondObject() == null
   assert    range1.getSecondObject() == "VA024RN_0025"
   assert    range2.getSecondObject() == "VA024RN_0025"
   assert    range3.getSecondObject() == "VA024RN_0025"
   assert    range4.getSecondObject() == "VA024RN_0025"

  }
  // hasExtendedRef()
  @Test
  void testHasExtendedRef(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.hasExtendedRef() == false
   assert notRange2.hasExtendedRef() == false
   assert notRange3.hasExtendedRef() == true
   assert notRange4.hasExtendedRef() == true
   assert    range1.hasExtendedRef() == false
   assert    range2.hasExtendedRef() == false
   assert    range3.hasExtendedRef() == false
   assert    range4.hasExtendedRef() == false

  }
  // hasObjectId()
  @Test
  void testHasObjectId(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn doesNotHave = new CiteUrn("urn:cite:hmt:vaimg")

   assert notRange1.hasObjectId() == true
   assert notRange2.hasObjectId() == true
   assert notRange3.hasObjectId() == true
   assert notRange4.hasObjectId() == true
   assert    range1.hasObjectId() == false
   assert    range2.hasObjectId() == false
   assert    range3.hasObjectId() == false
   assert    range4.hasObjectId() == false 
   assert    doesNotHave.hasObjectId() == false

  }
  // hasVersion()
  @Test
  void testHasVersion(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.hasVersion() == false
   assert notRange2.hasVersion() == true
   assert notRange3.hasVersion() == true
   assert notRange4.hasVersion() == false
   assert    range1.hasVersion() == false
   assert    range2.hasVersion() == false
   assert    range3.hasVersion() == false
   assert    range4.hasVersion() == false

  }
  // isRange()
  @Test
  void testIsRange(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.isRange() ==  false
   assert notRange2.isRange() ==  false
   assert notRange3.isRange() ==  false
   assert notRange4.isRange() ==  false
   assert    range1.isRange() == true
   assert    range2.isRange() == true
   assert    range3.isRange() == true
   assert    range4.isRange() == true

  }
  // reduceToCollection()
  @Test
  void testReduceToCollection(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert notRange2.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert notRange3.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert notRange4.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert    range1.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert    range2.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert    range3.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert    range4.reduceToCollection() == "urn:cite:hmt:vaimg"

  }

  // reduceToObject()
  @Test
  void testReduceToObject(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange2.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange3.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange4.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert    range1.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"
   assert    range2.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"
   assert    range3.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"
   assert    range4.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"

  }

  // reduceToVersion()
  @Test
  void testReduceToVersion(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")
   CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")
   CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")

   assert notRange1.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange2.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
   assert notRange3.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
   assert notRange4.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert    range1.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025"
   assert    range2.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"
   assert    range3.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1"
   assert    range4.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"

  }
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
