package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCite2UrnManip {


  // hasObjectId()
  @Test
  void testHasObjectId(){
   Cite2Urn notRange1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013")
   Cite2Urn notRange2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013")

	 assert shouldFail{
	   Cite2Urn notRange3 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013@0.12,0.12,0.12,0.12")
	 }

   Cite2Urn notRange4 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@0.12,0.12,0.12,0.12")

   assert shouldFail {
     Cite2Urn notRange5 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013@0.12,0.12,0.12,0.12")
   }

   Cite2Urn range1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013-VA024RN_0025")
   assert shouldFail {
     Cite2Urn range2 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   }
   Cite2Urn range3 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   assert shouldFail {
     Cite2Urn range4 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013@0.1532,0.1021,0.4014,0.0225-VA024RN_0025@0.1532,0.1021,0.4014,0.0225")
   }

   Cite2Urn doesNotHave1 = new Cite2Urn("urn:cite2:hmt:vaimg:")
   Cite2Urn doesNotHave2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:")
	 assert shouldFail{
		 Cite2Urn doesNotHave3 = new Cite2Urn("urn:cite2:hmt:vaimg")
	 }
	 assert shouldFail{
		 Cite2Urn doesNotHave4 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1")
	 }

   assert notRange1.hasObjectId() == true
   assert notRange2.hasObjectId() == true
   assert notRange4.hasObjectId() == true

   assert    range1.hasObjectId() == false

   assert    range3.hasObjectId() == false

   assert    doesNotHave1.hasObjectId() == false
   assert    doesNotHave2.hasObjectId() == false

  }

  // hasVersion()
  @Test
  void testHasCollectionVersion(){

   Cite2Urn has1 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013")
   Cite2Urn has2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025")
   Cite2Urn has3 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:")
   Cite2Urn hasNot1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013")
   Cite2Urn hasNot2 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013-VA024RN_0025")
   Cite2Urn hasNot3 = new Cite2Urn("urn:cite2:hmt:vaimg:")

	 assert has1.hasCollectionVersion()
	 assert has2.hasCollectionVersion()
	 assert has3.hasCollectionVersion()
	 assert ( hasNot1.hasCollectionVersion() == false)
	 assert ( hasNot2.hasCollectionVersion() == false)
	 assert ( hasNot3.hasCollectionVersion() == false)
	}

  // isRange()
  @Test
  void testIsRange(){

   Cite2Urn range1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013-VA024RN_0025")
   Cite2Urn range2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025")
   Cite2Urn range3 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025")
   Cite2Urn range4 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1-VA024RN_0025@2,2,2,2")
   Cite2Urn range5 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1,1,1,1-VA024RN_0025")
   Cite2Urn range6 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013-VA024RN_0025@2,2,2,2")
   Cite2Urn rangeNot1 = new Cite2Urn("urn:cite2:hmt:vaimg:VA012RN_0013")
   Cite2Urn rangeNot2 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013")
   Cite2Urn rangeNot3 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:VA012RN_0013@1.1.1.1")
   Cite2Urn rangeNot4 = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:")
   Cite2Urn rangeNot5 = new Cite2Urn("urn:cite2:hmt:vaimg:")

	 assert range1.isRange()
	 assert range2.isRange()
	 assert range3.isRange()
	 assert range4.isRange()
	 assert range5.isRange()
	 assert range6.isRange()
	 assert (rangeNot1.isRange() == false)
	 assert (rangeNot2.isRange() == false)
	 assert (rangeNot3.isRange() == false)
	 assert (rangeNot4.isRange() == false)
	 assert (rangeNot5.isRange() == false)

	}

}
