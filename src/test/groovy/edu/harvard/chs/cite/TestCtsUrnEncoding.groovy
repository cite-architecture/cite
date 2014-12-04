package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnEncoding extends GroovyTestCase {


  String urnStr = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν"
  
  @Test void testEncoding() {
    String encoded
    try {
      CtsUrn urn = new CtsUrn(urnStr)
      encoded = urn.toString(true)
      System.err.println "${urnStr} encoded: ${encoded}"
      

    } catch (Exception e) {
      System.err.println "Failed to make urn from " + urnStr
    }
    try {
      CtsUrn encodedUrn = new CtsUrn(encoded)
      System.err.println "From ${encoded}, RTed to : " + encodedUrn.decode()
    } catch (Exception e) {
      System.err.println "Failed to make urn from " + encoded
    }
  }

  void testRaw() {
    String src =  "Μῆνιν"


    String encoded =    java.net.URLEncoder.encode(src, "UTF-8")
    System.err.println "${src} yields " + encoded
    System.err.println "RT to " + java.net.URLDecoder.decode(encoded,"UTF-8")

    String ex2 = "1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D"
    System.err.println "Ex. 2 yields " + java.net.URLDecoder.decode(ex2,"UTF-8")


    String ex3 = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D"
    System.err.println "Ex. 3 yields " + java.net.URLDecoder.decode(ex3,"UTF-8")
  }

  
}
