package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnPoint extends GroovyTestCase {


    /**
    * Tests constructors of CtsUrn class.
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



 
     }



 
}
