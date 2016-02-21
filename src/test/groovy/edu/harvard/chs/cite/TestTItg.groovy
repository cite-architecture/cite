package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTItg {

  @Test
  void testCtsNamespaces() {
    TextInventory ti = new TextInventory(new File("testdata/tiwexemplar.xml"))
    assert ti.textgroups.size() == 1

    def tgpair = ti.textgroups[0]
    assert tgpair[0] == "urn:cts:greekLit:tlg0012:"
    String label = tgpair[1].replaceAll(" ", "") 


    assert label == "Homer"
  }



}
