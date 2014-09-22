package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestCCExts extends GroovyTestCase {


  def imgColls =  ["urn:cite:hmt:vaimg","urn:cite:hmt:vbimg"]

  @Test void testExtensions() {
    Citable cit = new Citable()
    cit.extensionsMap["image"] =  imgColls

    System.err.println "Ex.map is a " +  cit.extensionsMap.getClass()

  }




}
