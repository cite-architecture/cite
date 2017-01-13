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

  // isRange()

}
