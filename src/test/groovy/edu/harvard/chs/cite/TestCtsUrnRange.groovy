package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnRange extends GroovyTestCase {


    /**
    * Tests constructors and toString() method of CtsUrn class.
    */
    void testConstructor() {

      shouldFail {
	def brokenRange = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001:1.25-")
      }


      def pointUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1.1" 
      CtsUrn pointUrn = new CtsUrn(pointUrnStr)
      assert pointUrn.isRange() == false

      def testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1.1-1.24" 
      CtsUrn urn = new CtsUrn(testUrnStr)

      assert urn.isRange()
     }



 
}
