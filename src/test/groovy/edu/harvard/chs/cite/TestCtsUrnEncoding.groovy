package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnEncoding extends GroovyTestCase {




  void testInAndOut () {
    // CtsUrn is identical, whether constructed from encoded or raw 
    // form.  This encoded example is a valid IRI:  the raw form is not.
    String rawSubref = "urn:cts:latinLit:stoa0115.stoa002:preface.1@nunc"
    String encodedSubref = "urn:cts:latinLit:stoa0115.stoa002:preface.1%40nunc%5B1%5D"

    CtsUrn urn = new CtsUrn(rawSubref)
    assert urn.toString() == rawSubref
    assert urn.toString(true) == encodedSubref

    CtsUrn urn2 = new CtsUrn(encodedSubref)
    assert urn.toString() == rawSubref
    assert urn.toString(true) == encodedSubref
  }

 
}
