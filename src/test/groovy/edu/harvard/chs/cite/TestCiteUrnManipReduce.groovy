package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManipReduce {

  @Test
  void testObjectManipulation() {
    CiteUrn versionImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225")
    assert versionImgWRoi.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
    assert versionImgWRoi.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"


    assert shouldFail {
      CiteUrn objectImgWRoi = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225")
    }

  }

}
