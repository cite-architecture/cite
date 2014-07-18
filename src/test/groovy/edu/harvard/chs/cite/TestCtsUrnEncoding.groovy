package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnEncoding extends GroovyTestCase {


  String subrefEg = "urn:cts:latinLit:stoa0115.stoa002:preface.1@nunc"
  String expectedEncoding = "urn:cts:latinLit:stoa0115.stoa002:preface.1%40nunc%5B1%5D"

  void testInAndOut () {
    CtsUrn urn = new CtsUrn(subrefEg)
    assert urn.toString() == subrefEg
    assert urn.toString(true) == expectedEncoding
  }

 
}
