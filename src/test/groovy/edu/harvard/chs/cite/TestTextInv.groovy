package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTextInv {

  @Test
  void testExemplar() {
	  TextInventory ti = new TextInventory(new File("testdata/testinventory2.xml"))

  }

  @Test
  void testLabels() {
	  TextInventory ti = new TextInventory(new File("testdata/tiwexemplar.xml"))
	  String editionLabel = ti.versionLabel(new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.testlines:"))
	  assert editionLabel == " testlines "
	  String exemplarLabel = ti.versionLabel(new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.testlines.lextokens:"))
	  assert exemplarLabel == "Analysis as lexical tokens"

  }



}
