package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnRange extends GroovyTestCase {


    /**
    * Tests distinction of points and valid ranges.
    */
    void testRangeMethods() {

      String brokenRangeStr =  "urn:cts:greekLit:tlg0012.tlg001:1.25-"
      shouldFail {
	CtsUrn brokenRange = new CtsUrn(brokenRangeStr)
      }

      String emptySubStr1 =  "urn:cts:greekLit:tlg0012.tlg001:1.1-1.24@"
      shouldFail {
	CtsUrn emptySub1 = new CtsUrn(emptySubStr1)
      }

      String emptySubStr2 =  "urn:cts:greekLit:tlg0012.tlg001:1.1@-1.24"
      shouldFail {
	CtsUrn emptySub2 = new CtsUrn(emptySubStr2)
      }


      String pointUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1.1" 
      CtsUrn pointUrn = new CtsUrn(pointUrnStr)
      assert pointUrn.isRange() == false
      shouldFail {
	assert pointUrn.getRangeBegin()
      }

      shouldFail {
	assert pointUrn.getRangeEnd()
      }

      def testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1.1-1.24" 
      CtsUrn urn = new CtsUrn(testUrnStr)
      assert urn.isRange()
      assert urn.getRangeBegin() == "1.1"
      assert urn.getRangeEnd() == "1.24"

     }
}
