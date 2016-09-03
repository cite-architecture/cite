
package io.github.cite_architecture.cite

// Modelled on examples in this blog post:
//http://blog.scalac.io/2015/03/27/specs2-notes.html
//
// I've still got tons to learn about specs2.
//
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

  class TestCtsUrnSubrefs extends Specification {

  class Context extends Scope {
    val noSubref = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")
    val psgSubref = CtsUrn( "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@wrath")
    val psgSubrefIndexed = CtsUrn( "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@wrath[1]")

    val rangeBeginSubref = CtsUrn( "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@wrath-1.10")
    val rangeBeginIndexedSubref = CtsUrn( "urn:cts:greekLit:tlg0012.tlg001.msA:1.1@wrath[1]-1.10")

    val rangeEndSubref = CtsUrn( "urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.2@dogs")
  }


  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1'" should {
    "have an empty string for subref" in new Context {
      psgSubref.passageNodeSubref == ""
    }
  }
  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1'" should {
    "have an empty string for range begin subref" in new Context {
      psgSubref.rangeBeginSubref == ""
    }
  }
  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1'" should {
    "have an empty string for range end subref" in new Context {
      psgSubref.rangeEndSubref == ""
    }
  }


  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1@wrath'" should {
    "have a subref" in new Context {
      psgSubref.passageNodeSubref == "wrath"
    }
  }

  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1@wrath[1]'" should {
    "have a subref" in new Context {
      psgSubrefIndexed.passageNodeSubref == "wrath[1]"
    }
  }

  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1@wrath-1.10'" should {
    "have a subref on the range beginning"in new Context {
      rangeBeginSubref.rangeBeginSubref == "wrath"
    }
  }

  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1@wrath-1.10'" should {
    "have an empty string for subref on the range end"in new Context {
      rangeBeginSubref.rangeEndSubref == ""
    }
  }

  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1@wrath[1]-1.10'" should {
    "have a subref on the range beginning"in new Context {
      rangeBeginSubref.rangeBeginSubref == "wrath[1]"
    }
  }

  "The 'urn:cts:greekLit:tlg0012.tlg.001:1.1@wrath[1]-1.10'" should {
    "have an empty string for subref on the range end" in new Context {
      rangeBeginSubref.rangeEndSubref == ""
    }
  }

}
