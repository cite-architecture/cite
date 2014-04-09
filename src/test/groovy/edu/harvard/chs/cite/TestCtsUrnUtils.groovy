package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


class TestCtsUrnUtils extends GroovyTestCase {




    void testStripPassage() {
      String psgString = "urn:cts:greekLit:tlg0012.tlg001:1.25"
      CtsUrn psgUrn = new CtsUrn(psgString)
      assert psgUrn.getUrnWithoutPassage() == "urn:cts:greekLit:tlg0012.tlg001:"


      String workString = "urn:cts:greekLit:tlg0012.tlg001:"
      CtsUrn workUrn = new CtsUrn(workString)
      assert workUrn.getUrnWithoutPassage() == "urn:cts:greekLit:tlg0012.tlg001:"
      
    }


  void testReduceToWork() {
    String versString = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
    CtsUrn versUrn = new CtsUrn(versString)
    assert versUrn.reduceToWork() == "urn:cts:greekLit:tlg0012.tlg001:1.1"


    String workString = "urn:cts:greekLit:tlg0012.tlg001:1.1"
    CtsUrn workUrn = new CtsUrn(workString)
    String groupString = "urn:cts:greekLit:tlg0012"
    CtsUrn groupUrn = new CtsUrn(groupString)
    String emptyPsgString =  "urn:cts:greekLit:tlg0012.tlg001.msA:"
    CtsUrn emptyPsgUrn = new CtsUrn(emptyPsgString)

  }


    void testPointUrn() {

	 // access portions of reference component:
	 //assert urn.getLeafRefValue() == "1"

	 // level within work hiearchy is an enum that can be converted
	 // to and from String values:
	 //assert urn.labelForWorkLevel() == "work"
	 //assert urn.levelForLabel(urn.labelForWorkLevel()) == CtsUrn.WorkLevel.WORK	
     }

     void testRangeUrn() {
       //      	 def rangeUrnStr = "urn:cts:greekLit:tlg1220.tlg001:10-20" 
       // CtsUrn rangeUrn = new CtsUrn(rangeUrnStr)
       // assert rangeUrn.getLeafRefValue() == "10-20"
    }


    void testLimitUrn () {
      /*
	def longUrn = "urn:cts:latinLit:stoa0115.stoa002:2.1.1"
	CtsUrn urn = new CtsUrn(longUrn)
	assert urn.getPassage(1) == "2"
	assert urn.getPassage(2) == "2.1"
	assert urn.getPassage(3) == "2.1.1"

	assert urn.trimPassage(1) ==  "urn:cts:latinLit:stoa0115.stoa002:2"
	assert urn.trimPassage(2) ==  "urn:cts:latinLit:stoa0115.stoa002:2.1"
	assert urn.trimPassage(3) ==  "urn:cts:latinLit:stoa0115.stoa002:2.1.1"


        CtsUrn subUrn = new CtsUrn(subrefEg)
        assert subUrn.getPassage(1) == "preface"
        assert subUrn.getPassage(2) == "preface.1"
      */
    }

 
}
