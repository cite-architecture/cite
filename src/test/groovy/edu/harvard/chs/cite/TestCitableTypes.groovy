package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestCitableTypes extends GroovyTestCase {



  String txtUrn = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
  String objUrn = "urn:cite:hmt:msA.12r"
  String imgUrn = "urn:cite:hmt:vaimg.VA012RN-0013"
  String badUrn = "utterly:bogus:string"

  ArrayList imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]
  LinkedHashMap extMap = ["image" : imgColls]


  @Test void testExtList() {
    Citable cit = new Citable(extMap)
    assert cit.findCiteType(objUrn) == Citable.CiteType.OBJECT
    assert cit.findCiteType(txtUrn) == Citable.CiteType.TEXT
    assert cit.findCiteType(imgUrn) == Citable.CiteType.EXTENDED

    shouldFail {
      cit.findCiteType(badUrn)  
    }

  }




}
