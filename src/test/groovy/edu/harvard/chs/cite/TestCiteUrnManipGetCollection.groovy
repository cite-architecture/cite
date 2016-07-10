package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManipGetCollection {


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
    assert shouldFail {
      CiteUrn urn = new CiteUrn("urn:cite:hmt:vaimg.VA0012RN_0013@0.12,0.12,0.12,0.12-VA024RN_0025")
    }      
  }

}
