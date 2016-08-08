package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManipReduce {
  // reduceToCollection()
  @Test
  void testReduceToCollection(){
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

   assert notRange1.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert notRange2.reduceToCollection() == "urn:cite:hmt:vaimg"
   assert notRange3.reduceToCollection() == "urn:cite:hmt:vaimg"

   assert    range1.reduceToCollection() == "urn:cite:hmt:vaimg"

   assert    range3.reduceToCollection() == "urn:cite:hmt:vaimg"


  }

  // reduceToObject()
  @Test
  void testReduceToObject(){
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

   assert notRange1.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange2.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange3.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013"

   assert    range1.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"

   assert    range3.reduceToObject() == "urn:cite:hmt:vaimg.VA012RN_0013-VA024RN_0025"
  }

  // reduceToVersion()
  @Test
  void testReduceToVersion(){
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

   assert notRange1.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013"
   assert notRange2.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"
   assert notRange3.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1"

   assert    range1.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1"

   assert    range3.reduceToVersion() == "urn:cite:hmt:vaimg.VA012RN_0013.v1-VA024RN_0025.v1"


  }

}