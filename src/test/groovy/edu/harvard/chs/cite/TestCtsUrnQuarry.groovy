package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnQuarry extends GroovyTestCase {


    def subrefEg = "urn:cts:latinLit:stoa0115.stoa002:preface.1@nunc"

    /**
    * Tests basic methods of CtsUrn class.
    */
    void testPointUrn() {
	 // only form of constructor: URN from String:
	 shouldFail {
	 	    def badUrnSyntax = new CtsUrn("Dumb string")
	 }
	 shouldFail {
	 	    def tooShortSyntax = new CtsUrn("urn:cts:greekLit:")
	 }

      	 def testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1" 
	 CtsUrn urn = new CtsUrn(testUrnStr)

	 // round trips OK:
	 assert urn.toString() == testUrnStr

	 // access top-level components:
	 assert urn.getCtsNamespace() == "greekLit"
	 assert urn.getWorkComponent() == "tlg1220.tlg001"
	 assert urn.getPassageComponent() == '1'

	 // access portions of reference component:
	 assert urn.getLeafRefValue() == "1"


	 assert urn.getTextGroup(true) == "greekLit:tlg1220"
	 assert urn.getWork(true) == "greekLit:tlg001"

	 assert urn.getTextGroup(false) == "tlg1220"
	 assert urn.getWork(false) == "tlg001"


	 assert urn.getTextGroup() == "tlg1220"
	 assert urn.getWork() == "tlg001"


	 // A full URN for the work component is
	 // useful for many applications:
	 assert urn.getUrnWithoutPassage() == "urn:cts:greekLit:tlg1220.tlg001"

	 // level within work hiearchy is an enum that can be converted
	 // to and from String values:
	 assert urn.labelForWorkLevel() == "work"
	 assert urn.levelForLabel(urn.labelForWorkLevel()) == CtsUrn.WorkLevel.WORK	


         urn = new CtsUrn("urn:cts:greekLit:tlg1220.tlg001.chs01")
         assert urn.getVersion(true) == "greekLit:chs01"
         assert urn.getVersion(false) == "chs01"
         assert urn.getVersion() == "chs01"

         urn = new CtsUrn("urn:cts:latinLit:stoa0054.stoa010.hc")
	 println urn.getVersion()


 
     }

     /**
     * Tests methods specific to range Urns.
     */
     void testRangeUrn() {
      	 def rangeUrnStr = "urn:cts:greekLit:tlg1220.tlg001:10-20" 
	 CtsUrn rangeUrn = new CtsUrn(rangeUrnStr)
	 assert rangeUrn.getRangeBegin() == "10"
	 assert rangeUrn.getRangeEnd() == "20"
	 assert rangeUrn.getLeafRefValue() == "10-20"

	 assert rangeUrn.isRange()

         def homeRange = "urn:cts:greekLit:tlg0012.tlg001:1.1-1.10"
         CtsUrn homeRUrn = new CtsUrn(homeRange)
         assert homeRUrn.isRange()
    }


    void testLimitUrn () {
      /*
	def longUrn = "urn:cts:latinLit:stoa0115.stoa002:2.1.1"
	CtsUrn urn = new CtsUrn(longUrn)
	assert urn.getPassage(1) == "2"
	assert urn.getPassage(2) == "2.1"
	assert urn.getPassage(3) == "2.1.1"

	assert urn.trimPassage(1) ==  "urn:cts:latinLit:stoa0115.stoa002:2"
	assert urn.trimPassage(2) ==  "urn:cts:latinLit:stoa0115.stoa002:2.1"
	assert urn.trimPassage(3) ==  "urn:cts:latinLit:stoa0115.stoa002:2.1.1"


        CtsUrn subUrn = new CtsUrn(subrefEg)
        assert subUrn.getPassage(1) == "preface"
        assert subUrn.getPassage(2) == "preface.1"
      */
    }

 
}
