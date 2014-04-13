package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnWithSubref extends GroovyTestCase {

  void testSubref() {
    String noSubrefStr = "urn:cts:greekLit:tlg0012.tlg001:1.1"
    CtsUrn psg = new CtsUrn(noSubrefStr)
    assert psg.hasSubref() == false

    String subrefStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν"
    CtsUrn u = new CtsUrn(subrefStr)
    assert u.hasSubref()
    assert u.getSubref() == "μῆνιν"
    assert u.getSubrefIdx() == 1

    String moreMenin = "urn:cts:greekLit:tlg0012.tlg001:1.1@μ-1.1@ν[2]"
    CtsUrn subref2urn = new CtsUrn(moreMenin)
    assert subref2urn.hasSubref()
    assert subref2urn.getSubref2() == "ν"
    assert subref2urn.getSubrefIdx2() == 2


    String idxedSubrefStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@ν[2]"
    CtsUrn idxU = new CtsUrn(idxedSubrefStr)
    assert idxU.getSubrefIdx() == 2

    String nonIntStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν[x]"
    shouldFail {
      CtsUrn nonIntegerRef = new CtsUrn(nonIntStr)
    }
  }


  void testSubrefOnRanges() {
    String subrefStr = "urn:cts:greekLit:tlg0012.tlg001:1.1-1.2@οὐλομένην"  
    CtsUrn u = new CtsUrn(subrefStr)
    assert u.getSubref1() == null
    assert u.getSubref2() == "οὐλομένην"  
  }

}
