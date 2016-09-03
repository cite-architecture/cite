package io.github.cite_architecture

package cite {

  // Trait to implement for any object that is identified by a Urn.
  trait Citable {
    val urn: Urn
  }


  // The superclass of Urn objects, implemented by CtsUrn and CiteUrn
  class Urn {}

  case class CtsUrn  (val urnString: String) extends Urn {
    val components = urnString.split(":")

    require(components(0) == "urn", "Invalid URN syntax: " + urnString + ". First component must be 'urn'.")
    require(components(1) == "cts", "Invalid URN syntax: " + urnString) + ". Second component must be 'cts'."
    require(componentSyntaxOk, "Invalid URN syntax: " + urnString) + ". Wrong number of components."

    def namespace = components(2)
    def workComponent = components(3)
    def workParts = workComponent.split("\\.")
    require(workParts.size < 5, "Invalid URN syntax. Too many parts in work component " + workComponent )

    def workLevel = {
      workParts.size match {
        case 1 => WorkLevel.TextGroup
        case 2 => WorkLevel.Work
        case 3 => WorkLevel.Version
        case 4 => WorkLevel.Exemplar
      }
    }
    def passageComponent = {
      components.size match {
        case 5 => components(4)
        case _ => ""
      }
    }


    def componentSyntaxOk = {
      components.size match {
        case 5 => true
        case 4 => if (urnString.takeRight(1) == ":") true else false
        case _ => false
      }
    }
  }


  object WorkLevel extends Enumeration {
    val TextGroup, Work, Version, Exemplar = Value
  }

  case class CiteUrn  (val urnString: String) extends Urn {
    val components = urnString.split(":")
    //components
  }

}
