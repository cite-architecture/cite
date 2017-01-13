package edu.harvard.chs.cite

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestCite2UrnConstructVariousBadURNs {

	@Test
	void testConstructor1() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("xrn:cite2:hmt:vaimg:123")
		}
	}
	@Test
	void testConstructor2() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:xite2:hmt:vaimg:123")
		}
	}
	@Test
	void testConstructor3() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:vaimg:123")
		}
	}
	@Test
	void testConstructor4() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.vers.xx:123")
		}
	}
	@Test
	void testConstructor5() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:123-1-2")
		}
	}
	@Test
	void testConstructor6() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:dogs:123")
		}
	}

}
