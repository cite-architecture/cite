package io.github.cite_architecture

package cite {

  // Trait to implement for any object that is identified by a Urn.
  trait Citable {
    val urn: Urn
  }


  // The superclass of Urn objects, implemented by CtsUrn and CiteUrn
  sealed abstract class Urn {}

  case class CtsUrn  (val urnString: String) extends Urn {
    val components = urnString.split(":")

    // Verify syntax of submitted String:
    require(components(0) == "urn", "Invalid URN syntax: " + urnString + ". First component must be 'urn'.")
    require(components(1) == "cts", "Invalid URN syntax: " + urnString + ". Second component must be 'cts'.")
    require(componentSyntaxOk, "Invalid URN syntax: " + urnString + ". Wrong number of components.")
    require((workParts.size < 5), "Invalid URN syntax. Too many parts in work component " + workComponent )

    def namespace = components(2)

    def workComponent = components(3)
    def workParts = workComponent.split("\\.")
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
    def passageParts = passageComponent.split("-")
    val rangeBegin = if (passageParts.size > 1) passageParts(0) else ""
    val rangeEnd = if (passageParts.size > 1) passageParts(1) else ""
    val passageNode = if (passageParts.size == 1) passageParts(0) else ""
    require(passageSyntaxOk, "Invalid URN syntax.  Error in passage component " + passageComponent)

    val passageNodeSubref = subref(passageNode)
    val passageNodeSubrefText = subrefText(passageNode)
    val passageNodeSubrefIndex = subrefIndex(passageNode)

    val rangeBeginSubref = subref(rangeBegin)
    val rangeBeginSubrefText = subrefText(rangeBegin)
    val rangeBeginSubrefIndex = subrefIndex(rangeBegin)

    val rangeEndSubref = subref(rangeEnd)
    val rangeEndSubrefText = subrefText(rangeEnd)
    val rangeEndSubrefIndex = subrefIndex(rangeEnd)

    def isRange = {
      passageComponent contains "-"
    }


    def subref(s: String) = {
      val psgSplit = passageComponent.split("@")
      psgSplit.size match {
        case 2 => psgSplit(1)
        case _ => ""
      }
    }

    def subrefText(s: String) = {
      val psgSplit = passageComponent.split("@")
      psgSplit.size match {
        case 2 => psgSplit(0)
        case _ => ""
      }
    }

    // given a subref, extract an index if any
    // value is a string but guaranteed to be a valid Int
    def subrefIndex(subref: String) = {
      // hairball RE to extract indexing string
      // from within square brackets.
      val idxRE = """[^\[]+\[([^\]]+)\]""".r
      subref match {
        case idxRE(i) => try {
          i.toInt
        } catch {
          case e: NumberFormatException => None
        }
        case _ => None
      }
    }

    def componentSyntaxOk = {
      components.size match {
        case 5 => true
        case 4 => if (urnString.takeRight(1) == ":") true else false
        case _ => false
      }
    }

    def passageSyntaxOk = {
      passageParts.size match {
        case 1 => if (passageComponent.contains("-")) false else true
        case 2 => ((rangeBegin.size > 0) && (rangeEnd.size > 0))
      }
    }


    override def toString() = {
      urnString
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
