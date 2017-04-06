package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/** Class testing conversion from CITE to CITE2 urns */
class TestCite2Encode extends GroovyTestCase {

@Test void testEncoding1() {
		Cite2Urn c2urn = new Cite2Urn("urn:cite2:hmt:vaimg:123")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() == encodedUrnStr
 }

@Test void testEncoding2() {
		Cite2Urn c2urn = new Cite2Urn("urn:cite2:hmt:vaimg:123-456")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() == encodedUrnStr
 }

@Test void testEncoding3() {
		Cite2Urn c2urn = new Cite2Urn("urn:cite2:hmt:vaimg.v1:123@μῆνιν")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		Cite2Urn encodedUrn = new Cite2Urn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

@Test void testEncoding4() {
		Cite2Urn c2urn = new Cite2Urn("urn:cite2:hmt:vaimg.v1:123@μῆνιν-234@ἄειδε")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		Cite2Urn encodedUrn = new Cite2Urn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

@Test void testEncoding5() {
		Cite2Urn c2urn = new Cite2Urn("urn:cite2:hmt:vaimg.v1:123-234@ἄειδε")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		assert c2urn.toString() != encodedUrnStr
		Cite2Urn encodedUrn = new Cite2Urn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

@Test void testEncoding6() {
		Cite2Urn c2urn = new Cite2Urn("urn:cite2:hmt:vaimg.v1:123@μῆνιν-234")
		String encodedUrnStr = c2urn.encodeSubref()
		System.err.println("${c2urn} >> ${encodedUrnStr}")
		System.err.println("${encodedUrnStr} >> ${new Cite2Urn(encodedUrnStr)}")
		assert c2urn.toString() != encodedUrnStr
		Cite2Urn encodedUrn = new Cite2Urn(encodedUrnStr)
		assert c2urn.toString() == encodedUrn.toString()
 }

}
