
package io.github.cite_architecture.cite

// Modelled on examples in this blog post:
//http://blog.scalac.io/2015/03/27/specs2-notes.html
//
// I've still got tons to learn about specs2.
//
import org.specs2.mutable.Specification

class TestCtsUrnValidation extends Specification {

  "A CtsUrn" should {
   "throw an IllegalArgumentException if too few components" in {

     CtsUrn("urn:cts:greekLit:tlg0012.tlg001") must  throwA[java.lang.IllegalArgumentException]
   }
 }

 "A CtsUrn" should {
  "throw an IllegalArgumentException if 'urn' component is missing" in {

    CtsUrn("XX:cts:greekLit:tlg0012.tlg001:") must  throwA[java.lang.IllegalArgumentException]
    }
  }

  "A CtsUrn" should {
    "throw an IllegalArgumentException if required 'cts' component is missing" in {
     CtsUrn("urn:XX:greekLit:tlg0012.tlg001:") must  throwA[java.lang.IllegalArgumentException]
    }
  }

  "A CtsUrn" should {
    "throw an IllegalArgumentException if work hierarchy exceeds 4 levels" in {
     CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.tokens.subexemplar:") must  throwA[java.lang.IllegalArgumentException]
    }
  }

  "A CtsUrn" should {
    "throw a scala.MatchError if a range has more than two elements" in {
     CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.10-1.17") must  throwA[scala.MatchError]
    }
  }

  "A CtsUrn" should {
    "throw an IllegalArgumentException if a range has an empty beginning node" in {
     CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:-1.10") must  throwA[java.lang.IllegalArgumentException]
    }
  }

  "A CtsUrn" should {
    "throw an IllegalArgumentException if a range has an empty ending node" in {
     CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-") must  throwA[java.lang.IllegalArgumentException]
    }
  }

}
