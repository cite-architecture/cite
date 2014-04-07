package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnWithSubref extends GroovyTestCase {


    def subrefEg = "urn:cts:greekLit:tlg0012.tlg001.testlines:1.1@sub"



    void testSub() {
      CtsUrn u = new CtsUrn(subrefEg)
      assert u
    }

 
}
