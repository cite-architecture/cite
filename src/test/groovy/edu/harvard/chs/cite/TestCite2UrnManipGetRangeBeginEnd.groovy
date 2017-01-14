package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCite2UrnManipGetRangeBeginEnd{
  @Test
  void testGetRangeBegin(){

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

	 assert range1.getRangeBegin().toString() == "urn:cite2:hmt:vaimg:VA012RN_0013"
	 assert range2.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013"
	 assert range3.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013"
	 assert range4.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1"
	 assert range5.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1"
	 assert range6.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013"
	 assert rangeNot1.getRangeBegin().toString() == "urn:cite2:hmt:vaimg:VA012RN_0013"
	 assert rangeNot2.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013"
	 assert rangeNot3.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1"
	 assert rangeNot4.getRangeBegin().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert rangeNot5.getRangeBegin().toString() == "urn:cite2:hmt:vaimg:"

  }

  @Test
  void testGetRangeEnd(){

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

	 assert range1.getRangeEnd().toString() == "urn:cite2:hmt:vaimg:VA024RN_0025"
	 assert range2.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA024RN_0025"
	 assert range3.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA024RN_0025"
	 assert range4.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA024RN_0025@2,2,2,2"
	 assert range5.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA024RN_0025"
	 assert range6.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA024RN_0025@2,2,2,2"
	 assert rangeNot1.getRangeEnd().toString() == "urn:cite2:hmt:vaimg:VA012RN_0013"
	 assert rangeNot2.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013"
	 assert rangeNot3.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1"
	 assert rangeNot4.getRangeEnd().toString() == "urn:cite2:hmt:vaimg.rel1:"
	 assert rangeNot5.getRangeEnd().toString() == "urn:cite2:hmt:vaimg:"

  }
}
