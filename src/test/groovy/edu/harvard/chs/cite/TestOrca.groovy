package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


class TestOrca  {

  @Test
  void testAnalysis() {
    CiteUrn analysisRec = new CiteUrn("urn:cite:hmt:classtokens.1")
    CtsUrn psg = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν")
    CiteUrn analysisObj = new CiteUrn("urn:cite:hmt:tokens.lexical")
    String xformed = "μῆνιν"


    Orca orca = new Orca(analysisRec,psg, analysisObj, xformed)

    assert orca.getTransformedText() == xformed
    assert orca.getPassageAnalyzed().toString() == psg.toString()
    assert orca.getAnalysisObject().toString() == analysisObj.toString()
  }
}
