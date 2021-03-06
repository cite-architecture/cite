package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


import java.text.Normalizer
import java.text.Normalizer.Form



/** Tests serialization of CtsUrn objects.
*/
class TestCtsUrnSerial {


  @Test
  void testUnicodeEncodeSubref() {
    // should always be NFC
    CtsUrn urn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν-1.2@οὐλομένην")
    String encoded = urn.encodeSubref()
    assert urn.toString() == URLDecoder.decode(encoded, "UTF-8")
  }

  @Test
  void testUnicodeEncodeChars() {
    CtsUrn urnEpsilonTonos = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@οὐλομένην")
    CtsUrn urnEpsilonOxia = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@οὐλομένην")
    CtsUrn urnEpsilonCombining = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@οὐλομένην")
	assert urnEpsilonTonos.toString() == urnEpsilonOxia.toString()
	assert urnEpsilonTonos.toString() == urnEpsilonCombining.toString()
	assert urnEpsilonOxia.toString() == urnEpsilonCombining.toString()
  }


  @Test
  void testUnicodeNormalization() {

		String test1 = "οὐλομένην" 
		String test2 = "οὐλομένην"
		String test3 = "οὐλομένην"

		String urnString1 = "urn:cts:greekLit:tlg0012.tlg001:2.1@" + test1
		String urnString2 = "urn:cts:greekLit:tlg0012.tlg001:2.1@" + test2
		String urnString3 = "urn:cts:greekLit:tlg0012.tlg001:2.1@" + test3

		shouldFail {
			assert urnString1 == urnString2
			assert urnString1 == urnString3
			assert urnString2 == urnString3
		}

		CtsUrn urn1 = new CtsUrn(urnString1)
		CtsUrn urn2 = new CtsUrn(urnString2)
		CtsUrn urn3 = new CtsUrn(urnString3)

		assert urn1.toString() == urn2.toString()
		assert urn1.toString() == urn3.toString()
		assert urn2.toString() == urn3.toString()

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
    CtsUrn urn1 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν-1.2@οὐλομένην")
    CtsUrn urn2 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν[1]")
    CtsUrn urn3 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")
    CtsUrn urn4 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2")
    CtsUrn urn5 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1")
    CtsUrn urn6 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
    CtsUrn urn7 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001:")
    CtsUrn urn8 = new CtsUrn("urn:cts:greekLit:tlg0012:")
    String encoded1 = urn1.encodeSubref()
	println "${urn1} >> ${encoded1}"
    String encoded2 = urn2.encodeSubref()
	println "${urn2} >> ${encoded2}"
    String encoded3 = urn3.encodeSubref()
	println "${urn3} >> ${encoded3}"
    String encoded4 = urn4.encodeSubref()
	println "${urn4} >> ${encoded4}"
    String encoded5 = urn5.encodeSubref()
	println "${urn5} >> ${encoded5}"
    String encoded6 = urn6.encodeSubref()
	println "${urn6} >> ${encoded6}"
    String encoded7 = urn7.encodeSubref()
	println "${urn7} >> ${encoded7}"
    String encoded8 = urn8.encodeSubref()
	println "${urn8} >> ${encoded8}"
	assert urn1.toString() == URLDecoder.decode(encoded1, "UTF-8")
	assert urn2.toString() == URLDecoder.decode(encoded2, "UTF-8")
	assert urn3.toString() == URLDecoder.decode(encoded3, "UTF-8")
	assert urn4.toString() == URLDecoder.decode(encoded4, "UTF-8")
	assert urn5.toString() == URLDecoder.decode(encoded5, "UTF-8")
	assert urn6.toString() == URLDecoder.decode(encoded6, "UTF-8")
	assert urn7.toString() == URLDecoder.decode(encoded7, "UTF-8")
  }
	
	@Test
	void testToString(){
	String testString = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν[1]-1.2@οὐλομένην[1]"
    CtsUrn urn1 = new CtsUrn(testString)
    CtsUrn urn2 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν[1]")
    CtsUrn urn3 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")
    CtsUrn urn4 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2")
    CtsUrn urn5 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1")
    CtsUrn urn6 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
    CtsUrn urn7 = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001:")
    CtsUrn urn8 = new CtsUrn("urn:cts:greekLit:tlg0012:")

	// Library should add indices to subrefs!

	assert urn1.toString() == testString
	
    assert urn2.toString() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν[1]"
    assert urn3.toString() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
    assert urn4.toString() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2"
    assert urn5.toString() == "urn:cts:greekLit:tlg0012.tlg001.msA:1"
    assert urn6.toString() == "urn:cts:greekLit:tlg0012.tlg001.msA:"
    assert urn7.toString() == "urn:cts:greekLit:tlg0012.tlg001:"
    assert urn8.toString() == "urn:cts:greekLit:tlg0012:"
	}

}
