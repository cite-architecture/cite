
package io.github.cite_architecture.cite

// Modelled on examples in this blog post:
//http://blog.scalac.io/2015/03/27/specs2-notes.html
//
// I've still got tons to learn about specs2.
//
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class TestCtsUrnComponents extends Specification {
  class Context extends Scope {
    val urnString = "urn:cts:greekLit:tlg0012.tlg001.msA:1.1"
    val urn = CtsUrn(urnString)
  }

  "A CtsUrn" should {
    "have a namespace" in new Context {
      urn.namespace == "greekLit"
    }
  }
  "A CtsUrn" should {
    "have a work component" in new Context {
      urn.workComponent == "tlg0012.tlg001.msA"
    }
  }
  "A CtsUrn" should {
    "determine a work level" in new Context {
      urn.workLevel == WorkLevel.Version
    }
  }
  // etc etc etc  Many units to implement...

}
