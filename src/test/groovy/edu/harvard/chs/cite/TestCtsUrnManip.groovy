package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test



class TestCtsUrnManip extends GroovyTestCase {
  

  void testNodeManipulation() {
    CtsUrn versionRef = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@μῆνιν")
    String actual = versionRef.reduceToNode()
    String expected = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
    assert actual == expected
    
    CtsUrn workRef = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν")
    assert workRef.reduceToNode() == "urn:cts:greekLit:tlg0012.tlg001:1.1"

  }

  void testRangeManipulation() {

    CtsUrn versionRange = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2@οὐλομένην")
    assert versionRange.reduceToNode() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2"

    
    CtsUrn workRange = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2@οὐλομένην")
    assert workRange.reduceToNode() == "urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2"
    
  }
  
}
