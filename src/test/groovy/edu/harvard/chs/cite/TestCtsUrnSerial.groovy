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
	println "${urn1} >> ${encoded1}"
    String encoded2 = urn2.encodeSubref()
	println "${urn2} >> ${encoded2}"
    String encoded3 = urn3.encodeSubref()
	println "${urn3} >> ${encoded3}"
    String encoded4 = urn4.encodeSubref()
	println "${urn4} >> ${encoded4}"
	assert urn1.toString() == URLDecoder.decode(encoded1, "UTF-8")
	assert urn2.toString() == URLDecoder.decode(encoded2, "UTF-8")
	assert urn3.toString() == URLDecoder.decode(encoded3, "UTF-8")
	assert urn4.toString() == URLDecoder.decode(encoded4, "UTF-8")

  }


}
