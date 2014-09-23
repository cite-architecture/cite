package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestCiteExtensions extends GroovyTestCase {



  CtsUrn txtUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")
  CiteUrn objUrn = new CiteUrn("urn:cite:hmt:msA.12r")
  CiteUrn imgUrn = new CiteUrn("urn:cite:hmt:vaimg.VA012RN-0013")

  ArrayList imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]
  ArrayList geoPhotos = ["urn:cite:hmt:vaimg"]
  LinkedHashMap extMap = ["image" : imgColls, "geo" : geoPhotos]

  Citable cit = new Citable(extMap)

  @Test void testExtList() {
    ArrayList vaExtensions = cit.getExtensions(imgUrn)
    ArrayList expectedExts = ["image", "geo"]
    assert expectedExts as Set == vaExtensions as Set
  }


  @Test void testUnextended() {
    assert cit.getExtensions(objUrn) == null
  }


  @Test void testInvalid() {
    shouldFail {
      def textExtensions = cit.getExtensions(txtUrn)
    }
  }


}
