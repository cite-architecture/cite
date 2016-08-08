package edu.harvard.chs.cite



import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail




class TestCiteUrnManipRangeReduce {

  @Test
  void testRangeManipulation() {
    CiteUrn mixedLevels = new CiteUrn("urn:cite:examples:bifolios.1.v1-3.v1")
    assert mixedLevels.reduceToObject() == "urn:cite:examples:bifolios.1-3"
    assert mixedLevels.reduceToVersion() == "urn:cite:examples:bifolios.1.v1-3.v1"
  }


}
