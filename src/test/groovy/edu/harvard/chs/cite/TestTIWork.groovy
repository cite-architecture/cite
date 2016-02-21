package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTIWork {

  @Test
  void testCtsWork() {
    TextInventory ti = new TextInventory(new File("testdata/tiwexemplar.xml"))
    assert ti.works.size() == 1

    CtsUrn expectedUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001:")
    def triple = ti.works[0]

    CtsUrn actualUrn = new CtsUrn(triple[0])
    assert expectedUrn.toString() == actualUrn.toString()

    String expectedLabel = "Iliad"
    assert expectedLabel == triple[1].replaceAll(" ", "")
  }



}
