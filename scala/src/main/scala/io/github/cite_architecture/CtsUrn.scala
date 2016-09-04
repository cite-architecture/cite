package io.github.cite_architecture

package cite {

  /** Trait for any citable scholarly resource.
  *
  * Implementing classes must have a Urn value
  * identifying the object.  An example in the cite
  * library is the Orca class.
  */
  trait Citable {
    val urn: Urn
  }


  /** The superclass of Urn objects, implemented by CtsUrn and CiteUrn.
  */
  sealed abstract class Urn {}


  /** A URN for a canonically text or passage of text.
  *
  * @constructor create a new CtsUrn
  * @param urnString String representation of CtsUrn validating
  * againt the CtsUrn specification
  */
  case class CtsUrn  (val urnString: String) extends Urn {
    val components = urnString.split(":")

    // Verify syntax of submitted String:
    require(components(0) == "urn", "Invalid URN syntax: " + urnString + ". First component must be 'urn'.")
    require(components(1) == "cts", "Invalid URN syntax: " + urnString + ". Second component must be 'cts'.")
    require(componentSyntaxOk, "Invalid URN syntax: " + urnString + ". Wrong number of components.")
    require((workParts.size < 5), "Invalid URN syntax. Too many parts in work component " + workComponent )

    /** Required namespace component of the URN.*/
    def namespace = components(2)

    /** Required work component of the URN.*/
    def workComponent = components(3)
    /** Array of dot-separate parts of the workComponent.*/
    def workParts = workComponent.split("\\.")
    /** Enumerated WorkLevel for this workComponent.*/
    def workLevel = {
      workParts.size match {
        case 1 => WorkLevel.TextGroup
        case 2 => WorkLevel.Work
        case 3 => WorkLevel.Version
        case 4 => WorkLevel.Exemplar
      }
    }

    /** Optional passage component of the URN.
    *
    * Value is an empty string if there is no passage component.
    */
    def passageComponent = {
      components.size match {
        case 5 => components(4)
        case _ => ""
      }
    }
    /** Array of hyphen-separated parts of the passageComponent.
    *
    * The Array will contain 0 elements if passageComponent is empty,
    * 1 element if the passageComponent is a node reference, and
    * 2 elements if the passageComponent is a range reference.
    */
    def passageParts = passageComponent.split("-")
    /** First range part of the passage component of the URN.
    *
    * Value is an empty string if there is no passage component
    * or if the passage component is a node reference.
    */
    val rangeBegin = if (passageParts.size > 1) passageParts(0) else ""
    /** First range part of the passage component of the URN.
    *
    * Value is an empty string if there is no passage component
    * or if the passage component is a node reference.
    */
    val rangeEnd = if (passageParts.size > 1) passageParts(1) else ""
    /** Single node of the passage component of the URN.
    *
    * Value is an empty string if there is no passage component
    * or if the passage component is a range reference.
    */
    val passageNode = if (passageParts.size == 1) passageParts(0) else ""
    require(passageSyntaxOk, "Invalid URN syntax.  Error in passage component " + passageComponent)

    /** Full string value of the passage node's subref.*/
    val passageNodeSubref = subref(passageNode)
    /** Indexed text of the passage node's subref.*/
    val passageNodeSubrefText = subrefText(passageNode)
    /** Index value of the passage node's subref.*/
    val passageNodeSubrefIndex = subrefIndex(passageNode)


    /** Full string value of the range beginning's subref.*/
    val rangeBeginSubref = subref(rangeBegin)
    /** Indexed text of the range beginning's subref.*/
    val rangeBeginSubrefText = subrefText(rangeBegin)
    /** Index value of the range beginning's subref.*/
    val rangeBeginSubrefIndex = subrefIndex(rangeBegin)

    /** Full string value of the range ending's subref.*/
    val rangeEndSubref = subref(rangeEnd)
    /** Indexed text of the range ending's subref.*/
    val rangeEndSubrefText = subrefText(rangeEnd)
    /** Index value of the range ending's subref.*/
    val rangeEndSubrefIndex = subrefIndex(rangeEnd)

    /** True if the URN refers to a range.*/
    def isRange = {
      passageComponent contains "-"
    }

    /** Extracts the subref from a passage node value.
    *
    * @param s A passage node value, a reference to
    * a single node or to the beginning or ending node
    * of a range reference.
    */
    def subref(s: String) = {
      val psgSplit = passageComponent.split("@")
      psgSplit.size match {
        case 2 => psgSplit(1)
        case _ => ""
      }
    }

    /** Extracts the cited text of a subref from a passage node value.
    *
    * @param s A passage node value, a reference to
    * a single node or to the beginning or ending node
    * of a range reference.
    */
    def subrefText(s: String) = {
      val psgSplit = passageComponent.split("@")
      psgSplit.size match {
        case 2 => psgSplit(0)
        case _ => ""
      }
    }


    /** Extracts the explicitly given index of a subref
    * from a passage node value.
    *
    * The index value must be an integer.
    * @param s A passage node value, a reference to
    * a single node or to the beginning or ending node
    * of a range reference.
    */
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


    /** True if URN's syntax for required components is valid.*/
    def componentSyntaxOk = {
      components.size match {
        case 5 => true
        case 4 => if (urnString.takeRight(1) == ":") true else false
        case _ => false
      }
    }

    /** True if URN's syntax for optional passage component is valid.*/
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


  /** Enumeration of levels of the CTS work hierarchy. */
  object WorkLevel extends Enumeration {
    val TextGroup, Work, Version, Exemplar = Value
  }

  
  case class CiteUrn  (val urnString: String) extends Urn {
    val components = urnString.split(":")
    //components
  }

}
