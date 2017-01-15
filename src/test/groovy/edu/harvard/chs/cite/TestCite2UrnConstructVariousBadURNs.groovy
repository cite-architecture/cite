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
	@Test
	void testConstructor7() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg:123.1")
		}
	}

	@Test
	void testConstructor8() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:123.1")
		}
	}

	@Test
	void testConstructor9() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:123-124.1")
		}
	}

	@Test
	void testConstructor10() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:123.1@12,12,12,12-124")
		}
	}

	@Test
	void testConstructor11() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:123@12,12,12,12-124.1@13,13,13,13")
		}
	}

	@Test
	void testConstructor12() {
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:vaimg.rel1:123@12,12,12,12-124.1@13,13,13,13")
		}
	}

	@Test
	void testBadUrns1(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1:.12r")
		}
	}

	@Test
	void testBadUrns2(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1:12r.")
		}
	}

	@Test
	void testBadUrns3(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA..r1:12r")
		}
	}

	@Test
	void testBadUrns4(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1.:12r")
		}
	}

	@Test
	void testBadUrns5(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:.msA.r1:12r")
		}
	}

	@Test
	void testBadUrns6(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA..r1:12r")
		}
	}
	@Test
	void testBadUrns7(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1.:12r")
		}
	}
	@Test
	void testBadUrns8(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:.msA.r1:12r")
		}
	}

	@Test
	void testBadUrns9(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1:12r-")
		}
	}
	@Test
	void testBadUrns10(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1:-12r")
		}
	}
	@Test
	void testBadUrns11(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1:12r-14v-22r")
		}
	}
	@Test
	void testBadUrns12(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.r1:12r--14v")
		}
	}
	@Test
	void testBadUrns13(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.:12r-14v")
		}
	}
	@Test
	void testBadUrns14(){
		assert shouldFail{
			Cite2Urn versObj = new Cite2Urn("urn:cite2:hmt:msA.rel1:12.r")
		}
	}

}
