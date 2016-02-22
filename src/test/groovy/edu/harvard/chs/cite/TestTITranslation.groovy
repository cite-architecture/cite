package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTITranslation {


  TextInventory ti = new TextInventory(new File("testdata/tiwtranslation.xml"))

  CtsUrn expectedUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.testtranslation:")

  @Test
  void testTranslation() {
      assert ti.translationLanguages[expectedUrn.toString()] == 'eng'

  }



}
