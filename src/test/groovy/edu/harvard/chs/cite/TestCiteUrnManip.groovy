package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test



class TestCiteUrnManip extends GroovyTestCase {
  
  
  void testObjectManipulation() {
    CiteUrn versionImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225")
    assert versionImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
    
    CiteUrn objectImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225")
    assert objectImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
  }


  void testRangeManipulation() {
    CiteUrn mixedLevels = new CiteUrn("urn:cite:examples:bifolios.1.v1@verso-3.v1")
    assert mixedLevels.reduceToObject() == "urn:cite:examples:bifolios.1.v1-3.v1"
  }
}
