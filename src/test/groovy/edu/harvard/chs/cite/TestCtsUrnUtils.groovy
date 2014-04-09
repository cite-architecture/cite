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
    String groupString = "urn:cts:greekLit:tlg0012"
    CtsUrn groupUrn = new CtsUrn(groupString)
    assert shouldFail {
      String reduction = groupUrn.reduceToWork()
    }


    String workString = "urn:cts:greekLit:tlg0012.tlg001:1.1"
    CtsUrn workUrn = new CtsUrn(workString)
    assert workUrn.reduceToWork() == "urn:cts:greekLit:tlg0012.tlg001:1.1"

    String versString = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
    CtsUrn versUrn = new CtsUrn(versString)
    assert versUrn.reduceToWork() == "urn:cts:greekLit:tlg0012.tlg001:1.1"

    String exemplarString = "urn:cts:greekLit:tlg0012.tlg001.msA.tokens:1.1.1"
    CtsUrn exemplarUrn = new CtsUrn(exemplarString)
    assert exemplarUrn.reduceToWork() == "urn:cts:greekLit:tlg0012.tlg001:1.1.1"

    String emptyPsgString =  "urn:cts:greekLit:tlg0012.tlg001.msA:"
    CtsUrn emptyPsgUrn = new CtsUrn(emptyPsgString)
    assert emptyPsgUrn.reduceToWork() == "urn:cts:greekLit:tlg0012.tlg001:"

  }




  void testReduceToVersion() {
    String groupString = "urn:cts:greekLit:tlg0012"
    CtsUrn groupUrn = new CtsUrn(groupString)
    assert shouldFail {
      String reduction = groupUrn.reduceToVersion()
    }


    String workString = "urn:cts:greekLit:tlg0012.tlg001:1.1"
    CtsUrn workUrn = new CtsUrn(workString)
    assert shouldFail {
      String reduction = workUrn.reduceToVersion()
    }

    String versString = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
    CtsUrn versUrn = new CtsUrn(versString)
    assert versUrn.reduceToVersion() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"

    String exemplarString = "urn:cts:greekLit:tlg0012.tlg001.msA.tokens:1.1.1"
    CtsUrn exemplarUrn = new CtsUrn(exemplarString)
    assert exemplarUrn.reduceToVersion() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1.1"

    String emptyPsgString =  "urn:cts:greekLit:tlg0012.tlg001.msA:"
    CtsUrn emptyPsgUrn = new CtsUrn(emptyPsgString)
    assert emptyPsgUrn.reduceToVersion() == "urn:cts:greekLit:tlg0012.tlg001.msA:"
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
