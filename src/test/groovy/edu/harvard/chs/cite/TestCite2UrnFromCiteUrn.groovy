package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnFromCiteUrn {

	@Test void testConversion1() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:vaimg.123")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:vaimg:123"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion2() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:critsigns.diple")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:critsigns:diple"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion3() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:vaimg.VA082RN_0083")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:vaimg:VA082RN_0083"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion4() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:iliadicClauses.1.v1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:iliadicClauses.v1:1"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion5() {

		CiteUrn curn = new CiteUrn("urn:cite:perseusGreek:lexentity.lex115670.1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:perseusGreek:lexentity.1:lex115670"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion6() {

		CiteUrn curn = new CiteUrn("urn:cite:perseusGreek:lexentity.lex64224.1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:perseusGreek:lexentity.1:lex64224"
		assert curn.toCite2().toString() == c2urn.toString()
	}


	@Test void testConversion7() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:speech.1.v1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:speech.v1:1"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion8() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:vaimg.VA012RN_0013.v1@0.0417,0.0813,0.845,0.8813")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:vaimg.v1:VA012RN_0013@0.0417,0.0813,0.845,0.8813"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion9() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj.v1@12,12,12,12-obj2.v1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj@12,12,12,12-obj2"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion10() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj.v1-obj2.v1@13,13,13,13")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj-obj2@13,13,13,13"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion11() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj.v1@12,12,12,12-obj2.v1@13,13,13,13")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj@12,12,12,12-obj2@13,13,13,13"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion12() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1.v1-obj2.v1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj1-obj2"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion13() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1-obj2.v1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj1-obj2"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion14() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1-obj2")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll:obj1-obj2"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion15() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1.v1@12,12,12,12")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj1@12,12,12,12"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion16() {

		assert shouldFail{
			CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1@12,12,12,12")
			Cite2Urn c2urn = new Cite2Urn(curn)
			assert c2urn.toString() == "urn:cite2:hmt:coll:obj1@12,12,12,12"
			assert curn.toCite2().toString() == c2urn.toString()
		}
	}
	@Test void testConversion17() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1.v1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll.v1:obj1"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion18() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll.obj1")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll:obj1"
		assert curn.toCite2().toString() == c2urn.toString()
	}
	@Test void testConversion19() {

		CiteUrn curn = new CiteUrn("urn:cite:hmt:coll")
		Cite2Urn c2urn = new Cite2Urn(curn)
		assert c2urn.toString() == "urn:cite2:hmt:coll:"
		assert curn.toCite2().toString() == c2urn.toString()
	}



}
