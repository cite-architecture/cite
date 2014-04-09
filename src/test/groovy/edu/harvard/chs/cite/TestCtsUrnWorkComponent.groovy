package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnWorkComponent extends GroovyTestCase {


  String subrefEg = "urn:cts:latinLit:stoa0115.stoa002:preface.1@nunc"

  /**
   * Tests basic methods of CtsUrn class.
   */
  void testGroupAndWork() {
    
    String testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1" 
    CtsUrn urn = new CtsUrn(testUrnStr)

    assert urn.getTextGroup(true) == "greekLit:tlg1220"
    assert urn.getWork(true) == "greekLit:tlg001"

    assert urn.getTextGroup(false) == "tlg1220"
    assert urn.getWork(false) == "tlg001"

    assert urn.getTextGroup() == "tlg1220"
    assert urn.getWork() == "tlg001"
  }


  void testVersionAndExemplar() {
    String versionUrnStr = "urn:cts:greekLit:tlg0012.tlg001.msA:1.10"
    String tokenUrnStr = "urn:cts:greekLit:tlg0012.tlg001.msA.tokens:1.10.2"
  }


}
