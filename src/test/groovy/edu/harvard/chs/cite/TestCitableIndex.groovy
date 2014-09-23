package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test

// test 3 equivalent ways to configure CITE Collection
// extensions
class TestCitableIndex extends GroovyTestCase {


  LinkedHashMap expectedInverse =  ["urn:cite:hmt:vaimg":"image","urn:cite:hmt:vbimg" : "image"]

  ArrayList imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]


  @Test void testDirectAssignment() {
    Citable cit = new Citable()
    cit.extensionsMap["image"] =  imgColls
    cit.indexExtensions()
    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()
  }


  @Test void testSetter() {
    Citable cit = new Citable()
    LinkedHashMap extMap = ["image" : imgColls]
    cit.setCiteExtensions(extMap)
    
    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()
  }


  @Test void testConstructor() {
    LinkedHashMap extMap = ["image" : imgColls]
    Citable cit = new Citable(extMap)
    
    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()
  }

}
