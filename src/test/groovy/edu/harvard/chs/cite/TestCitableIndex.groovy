package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestCitableIndex extends GroovyTestCase {


  LinkedHashMap expectedInverse =  ["urn:cite:hmt:vaimg":"image","urn:cite:hmt:vbimg" : "image"]

  ArrayList imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]

  @Test void testUrnInInv() {
    Citable cit = new Citable()


    cit.extensionsMap["image"] =  imgColls
    cit.indexExtensions()

    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()

  }




  @Test void testTypes() {
    Citable cit = new Citable()

    ArrayList imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]
    LinkedHashMap extMap = ["image" : imgColls]
    cit.setCiteExtensions(extMap)
    
    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()
    
  }
}
