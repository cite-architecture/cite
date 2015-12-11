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

  @Test
  void testGetSubref(){
    CtsUrn urn1 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν-1.2@οὐλομένην")
    CtsUrn urn2 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν[1]")
    CtsUrn urn3 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")
    CtsUrn urn4 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2")
    String encoded1 = urn1.encodeSubref()
    String encoded2 = urn2.encodeSubref()
    String encoded3 = urn3.encodeSubref()
    String encoded4 = urn4.encodeSubref()

  }


}
