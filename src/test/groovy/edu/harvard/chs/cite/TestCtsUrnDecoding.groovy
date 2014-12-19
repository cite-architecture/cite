package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnDecoding extends GroovyTestCase {



    String rawSubref = "urn:cts:latinLit:stoa0115.stoa002:preface.1@nunc"
    String encodedSubref = "urn:cts:latinLit:stoa0115.stoa002:preface.1%40nunc%5B1%5D"

    void testPointUrn() {
      // test that internal processing of URN works whatever the form
      // of string it's initialized from
      CtsUrn urn = new CtsUrn(rawSubref)
      assert urn.getSubref() == "nunc"

      //CtsUrn urn2 = new CtsUrn(encodedSubref)
      //assert urn2.getSubref() == "nunc"
      
    }
     
}
