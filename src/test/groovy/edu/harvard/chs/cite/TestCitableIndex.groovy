package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestCitableIndex extends GroovyTestCase {

  def imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]

  @Test void testUrnInInv() {
    Citable cit = new Citable()
    cit.extensionsMap["image"] =  imgColls
    cit.indexExtensions()

    def expectedInverse =  ["urn:cite:hmt:vaimg":"image","urn:cite:hmt:vbimg" : "image"]
    
    assert expectedInverse.keySet() == cit.invertedExtensionsMap.keySet()

  }




}
