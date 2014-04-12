package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnSubrefIndexing extends GroovyTestCase {


     void testIndexing() {
      	 def rangeUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν[1]-1.2@ν[2]" 
	 CtsUrn rangeUrn = new CtsUrn(rangeUrnStr)
	 assert rangeUrn.isRange()


	 assert rangeUrn.getRangeBegin() == "1.1"
	 assert rangeUrn.getRangeEnd() == "1.2"

	 assert rangeUrn.getSubref1() == "μῆνιν"
	 assert rangeUrn.getSubrefIdx1() == 1

	 assert rangeUrn.getSubref2() == "ν"
	 assert rangeUrn.getSubrefIdx2() == 2

    }


 
}
