package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnWithSubref extends GroovyTestCase {



    void testSubref() {
      String noSubrefStr = "urn:cts:greekLit:tlg0012.tlg001:1.1"
      CtsUrn psg = new CtsUrn(noSubrefStr)
      shouldFail {
	assert psg.hasSubref()
      }

      String subrefStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν"

      CtsUrn u = new CtsUrn(subrefStr)
      assert u.hasSubref()

      assert u.getSubref1() == "μῆνιν"
      //assert u.getSubrefIdx1() == 1

    }

 
}
