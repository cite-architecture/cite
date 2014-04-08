package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnPoint extends GroovyTestCase {

    /**
    * Tests constructors and toString() method of CtsUrn class.
    */
    void testConstructor() {
      // only form of constructor: URN from String:
      shouldFail {
	 CtsUrn badUrnSyntax = new CtsUrn("Dumb string")
      }
      shouldFail {
	CtsUrn tooShortSyntax = new CtsUrn("urn:cts:greekLit:")
      }
      shouldFail {
	CtsUrn emptySubref = new CtsUrn("urn:cts:greekLit:tlg1220.tlg001:1.1@")
      }

      String testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1.1" 
      CtsUrn urn = new CtsUrn(testUrnStr)

      // simple to round trip when there is no subref:
      assert urn.toString() == testUrnStr


      // USE THIS TO TEST toString() with subreff:
      String subRefStr = "urn:cts:greekLit:tlg1220.tlg001:1.1@μῆνιν" 
      CtsUrn subRefUrn = new CtsUrn(subRefStr)
      System.err.println "Subref == " + subRefUrn.toString()




      /*
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
      */

     }



 
}
