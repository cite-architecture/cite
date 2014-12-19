package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test

import java.text.Normalizer
import java.text.Normalizer.Form


class TestCtsUrnToString extends GroovyTestCase {

  //  Form.NFC valid in RDF, so toString() method of CtsUrn class
  // alwqys returns Form.NFC.
  void testPointUrn() {
    String  pointUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν[1]" 
    CtsUrn pointUrn = new CtsUrn(pointUrnStr)
    assert pointUrn.toString() == Normalizer.normalize(pointUrnStr, Form.NFC)
  }

  void testRangeUrn() {
    String rangeUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1-1.2@ν[2]" 
    CtsUrn rangeUrn = new CtsUrn(rangeUrnStr)
    assert rangeUrn.isRange()
    assert rangeUrn.toString() == Normalizer.normalize(rangeUrnStr, Form.NFC)
  }


 
}
