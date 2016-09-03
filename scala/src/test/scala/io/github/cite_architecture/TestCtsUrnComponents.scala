
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
    val psgNode = CtsUrn( "urn:cts:greekLit:tlg0012.tlg001.msA:1.1")
    val rangeNode = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1-1.10")
  }

  "A CtsUrn" should {
    "have a namespace" in new Context {
      psgNode.namespace == "greekLit"
    }
  }
  "A CtsUrn" should {
    "have a work component" in new Context {
      psgNode.workComponent == "tlg0012.tlg001.msA"
    }
  }
  "A CtsUrn" should {
    "determine a work level" in new Context {
      psgNode.workLevel == WorkLevel.Version
    }
  }
  "The urn 'urn:cts:greekLit:tlg0012.tlg001.msA:1.1' " should {
    "have a passage component" in new Context {
      psgNode.passageComponent == "1.1"
    }
  }

  "The passage node 1.1" should {
    "not be a range" in new Context {
      psgNode.isRange == false
    }
  }

  "The passage node 1.1-1.10" should {
    "be a range" in new Context {
      rangeNode.isRange == true
    }
  }
  // etc etc etc  Many units to implement...

}
