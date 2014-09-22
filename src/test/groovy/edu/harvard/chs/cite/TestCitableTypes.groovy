package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestCitableTypes extends GroovyTestCase {



  String txtUrn = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
  String objUrn = "urn:cite:hmt:msA.12r"
  String imgUrn = "urn:cite:hmt:vaimg.VA012RN-0013"
  String badUrn = "utterly:bogus:string"

  ArrayList imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]

  @Test void testTypes() {

    System.err.println "IMG COLLS: " + imgColls.getClass()
    Citable cit = new Citable()
    cit.extensionsMap["image"] =  imgColls
    cit.indexExtensions()

    LinkedHashMap expectedInverse =  ["urn:cite:hmt:vaimg":"image","urn:cite:hmt:vbimg" : "image"]
    
    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()
  }




}
