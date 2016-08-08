package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManipGetFirstObj {

  // getFirstObject()
  @Test
  void testGetFirstObject(){
   CiteUrn notRange1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013")
   CiteUrn notRange2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1")
   CiteUrn notRange3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.12,0.12,0.12,0.12")
   assert shouldFail {
     CiteUrn notRange4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.12,0.12,0.12,0.12")
   }
   CiteUrn range1 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025")

   assert shouldFail {
     CiteUrn range2 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   }
   CiteUrn range3 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225-VA024RN_0025.v1@0.1532,0.1021,0.4014,0.0225")

   assert shouldFail {
     CiteUrn range4 = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   }

   assert notRange1.getFirstObject() ==  null
   assert notRange2.getFirstObject() ==  null
   assert notRange3.getFirstObject() ==  null
   assert range1.getFirstObject() == "VA012RN_0013"
   assert range3.getFirstObject() == "VA012RN_0013"


  }



}
