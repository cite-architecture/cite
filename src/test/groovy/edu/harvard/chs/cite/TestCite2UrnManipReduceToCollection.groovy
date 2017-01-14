package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCite2UrnManipReduceToCollection {
  @Test
  void testReduceToCollection(){

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

	 assert range1.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert range2.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert range3.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert range4.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert range5.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert range6.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert rangeNot1.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert rangeNot2.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert rangeNot3.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert rangeNot4.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"
	 assert rangeNot5.reduceToCollection().toString() == "urn:cite2:hmt:vaimg:"

  }

}
