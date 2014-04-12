package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnSubRefIndexing extends GroovyTestCase {

     void testPointIndexing() {
       String pointUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν[1]"
       CtsUrn pointUrn = new CtsUrn(pointUrnStr)
       assert pointUrn.hasSubref()

       assert pointUrn.getSubref() ==  "μῆνιν"
       assert pointUrn.getSubrefIdx() == 1


       shouldFail {
	 String sr1 = pointUrn.getSubref1()
       }

       shouldFail {
	Integer sr1idx = pointUrn.getSubrefIdx1()
       }



       shouldFail {
	 String sr2 = pointUrn.getSubref2()
       }
       shouldFail {
	Integer sr2idx = pointUrn.getSubrefIdx2()
       }


     }

     void testRangeIndexing() {
      	 String rangeUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν[1]-1.2@ν[2]" 
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
