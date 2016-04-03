package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTIExemplar {

  TextInventory ti = new TextInventory(new File("testdata/tiwexemplar.xml"))

  CtsUrn expectedUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.testlines.lextokens:")

  @Test
  void testExemplar() {
    assert ti.exemplars.size() == 1
    def quad = ti.exemplars[0]


    CtsUrn actualUrn = new CtsUrn(quad[0])
    assert expectedUrn.toString() == actualUrn.toString()

    String expectedLabel = "Analysisaslexicaltokens"
    assert expectedLabel == quad[1].replaceAll(" ", "")

    boolean expectedOnline = true
    assert expectedOnline == quad[2]

    String expectedParent = "urn:cts:greekLit:tlg0012.tlg001.testlines:"
    assert expectedParent == quad[3]

  }

  @Test
  void testParent() {
    assert ti.editions.size() == 1
    def quad = ti.editions[0]
    boolean expectedOnline = false
    assert expectedOnline == quad[2]
  }




}
