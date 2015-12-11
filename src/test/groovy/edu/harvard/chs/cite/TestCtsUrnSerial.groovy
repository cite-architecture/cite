package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


import java.text.Normalizer
import java.text.Normalizer.Form



/** Tests serialization of CtsUrn objects.
*/
class TestCtsUrnSerial {


  @Test
  void testUnicode() {
    // should always be NFC
    CtsUrn urn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν-1.2@οὐλομένην")
    String encoded = urn.encodeSubref()
    assert urn.toString() == URLDecoder.decode(encoded, "UTF-8")
  }

  @Test
  void testUnicodeNotRange() {
    // should always be NFC
    CtsUrn urn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν")
    String encoded = urn.encodeSubref()
    assert urn.toString() == URLDecoder.decode(encoded, "UTF-8")
  }


}
