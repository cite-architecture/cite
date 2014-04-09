package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test

class TestCtsUrnTopLevel extends GroovyTestCase {

  String subrefEg = "urn:cts:latinLit:stoa0115.stoa002:preface.1@nunc"

  void testPointUrn() {
    String testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1" 
    CtsUrn urn = new CtsUrn(testUrnStr)

    // access top-level components:
    assert urn.getCtsNamespace() == "greekLit"
    assert urn.getWorkComponent() == "tlg1220.tlg001"
    assert urn.getPassageComponent() == '1'
  }

}
