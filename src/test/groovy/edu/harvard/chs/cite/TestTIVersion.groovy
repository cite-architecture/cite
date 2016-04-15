package edu.harvard.chs.cite


import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail



/** Class to test cite library's CiteCollection class.
*/
class TestTIVersion {

  TextInventory ti = new TextInventory(new File("testdata/tiwexemplar.xml"))


  CtsUrn expectedUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.testlines:")

  @Test
  void testCtsEdition() {
    assert ti.editions.size() == 1
    def quad = ti.editions[0]

    CtsUrn actualUrn = new CtsUrn(quad[0])
    assert expectedUrn.toString() == actualUrn.toString()

    String expectedLabel = "testlines"
    assert expectedLabel == quad[1].replaceAll(" ", "")

    boolean expectedOnline = false
    assert quad[2] == expectedOnline

  }

  @Test
  void testVersionsForWork(){
	 CtsUrn u = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.testlines:")
     def l = ti.versionsForWork(u)
	 CtsUrn x = new CtsUrn(l[0])
	 assert "${u}" == "${x}"
	 
	 

  }



}
