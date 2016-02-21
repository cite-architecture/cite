package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTICtsns {

  @Test
  void testCtsNamespaces() {
    TextInventory ti = new TextInventory(new File("testdata/tiwexemplar.xml"))
    assert ti.ctsnamespaces.size() == 1
    def triple = ti.ctsnamespaces[0]
    assert triple[0] == "greekLit"
    assert triple[1] == "http://chs.harvard.edu/ctsns/greekLit"
    String labelNoWhite = triple[2].replaceAll(/[\s\n]/,'')
    System.err.println "Length of label "  + labelNoWhite.size()
    System.err.println labelNoWhite
    String expectedLabelNoWhite = """The"FirstThousandYearsofGreek"project'sinventoryofGreektexts."""
    assert expectedLabelNoWhite == labelNoWhite
  }



}
