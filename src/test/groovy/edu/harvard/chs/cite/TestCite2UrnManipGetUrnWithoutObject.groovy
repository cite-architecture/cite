package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCite2UrnManipGetUrnWithoutObject {
  @Test
  void testGetUrnWithoutObject(){

   Cite2Urn range1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013-VA024RN_0025")
   Cite2Urn range2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025")
   Cite2Urn range3 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025")
   Cite2Urn range4 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1-VA024RN_0025@2,2,2,2")
   Cite2Urn range5 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1-VA024RN_0025")
   Cite2Urn range6 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025@2,2,2,2")
   Cite2Urn rangeNot1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013")
   Cite2Urn rangeNot2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013")
   Cite2Urn rangeNot3 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1")
   Cite2Urn rangeNot4 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:")
   Cite2Urn rangeNot5 = new Cite2Urn("urn:cite2:hmt:vaimg:")

	 assert range1.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg:"
	 assert range2.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert range3.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert range4.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert range5.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert range6.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert rangeNot1.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg:"
	 assert rangeNot2.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert rangeNot3.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert rangeNot4.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert rangeNot5.getUrnWithoutObject().toString() == "urn:cite2:hmt:vaimg:"

  }

}
