package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test

class TestCtsUrnToString extends GroovyTestCase {

     void testPointUrn() {
       String  pointUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν[1]" 
       CtsUrn pointUrn = new CtsUrn(pointUrnStr)
       assert pointUrn.toString() == pointUrnStr

       String encodedStr = pointUrn.toString(true)
       assert pointUrnStr == java.net.URLDecoder.decode(encodedStr, "UTF-8")
     }

     void testRangeUrn() {
      	 String rangeUrnStr = "urn:cts:greekLit:tlg0012.tlg001:1.1-1.2@ν[2]" 
	 CtsUrn rangeUrn = new CtsUrn(rangeUrnStr)
	 assert rangeUrn.isRange()

	 String encodedStr = rangeUrn.toString(true)
	 assert java.net.URLDecoder.decode(encodedStr,"UTF-8") == rangeUrnStr
    }


 
}
