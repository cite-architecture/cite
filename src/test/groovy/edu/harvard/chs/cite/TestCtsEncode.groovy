package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Class testing conversion from CITE to CITE2 urns */
class TestCtsEncode extends GroovyTestCase {

@Test void testEncoding1() {
		CtsUrn c2urn = new CtsUrn("urn:cts:greeklit:tlg0012.tlg001.msa:1.2")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() == encodedUrnStr
 }

@Test void testEncoding2() {
		CtsUrn c2urn = new CtsUrn("urn:cts:greeklit:tlg0012.tlg001.msa:1.2-1.5")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() == encodedUrnStr
 }

@Test void testEncoding3() {
		CtsUrn c2urn = new CtsUrn("urn:cts:greeklit:tlg0012.tlg001.msa:1.1@μῆνιν[1]")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		CtsUrn encodedUrn = new CtsUrn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

@Test void testEncoding4() {
		CtsUrn c2urn = new CtsUrn("urn:cts:greeklit:tlg0012.tlg001.msa:1.2@μῆνιν[1]-1.1@ἄειδε[1]")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		CtsUrn encodedUrn = new CtsUrn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

@Test void testEncoding5() {
		CtsUrn c2urn = new CtsUrn("urn:cts:greeklit:tlg0012.tlg001.msa:1.2@μῆνιν[1]-1.1")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		CtsUrn encodedUrn = new CtsUrn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

@Test void testEncoding6() {
		CtsUrn c2urn = new CtsUrn("urn:cts:greeklit:tlg0012.tlg001.msa:1.2-1.1@ἄειδε[1]")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		CtsUrn encodedUrn = new CtsUrn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

}
