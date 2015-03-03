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


    String exemplarStr = "urn:cts:greekLit:tlg0012.tlg001.msA.tokens:"
    CtsUrn exemplarUrn = new CtsUrn(exemplarStr)

    // USE THIS TO TEST toString() with subreff:
    String subRefStr = "urn:cts:greekLit:tlg1220.tlg001:1.1@μῆνιν"
    CtsUrn subRefUrn = new CtsUrn(subRefStr)
    System.err.println "Subref == " + subRefUrn.toString()



    String noPsgString = "urn:cts:greekLit:tlg0012.tlg001:"
    CtsUrn noPsg = new CtsUrn(noPsgString)
    assert noPsg



      /*

      // access portions of reference component:
      assert urn.getLeafRefValue() == "1"


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
