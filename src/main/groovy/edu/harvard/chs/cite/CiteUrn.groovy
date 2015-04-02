package edu.harvard.chs.cite


import java.text.Normalizer
import java.text.Normalizer.Form


/**
* A class representing a reference to a versioned object in a set of
* objects with shared properties, expressed in the notation of the
* CITE Object URN system.  This class parses URNs expressed as Strings,
* and makes the components of the URN programattically accessible.
*
* Note that while the automatically generated groovydoc output does not
* show get* methods for all the CtsUrn's member properties,
* Groovy  compilation automatically creates those public methods.
*
*/
class CiteUrn {
  


  /** String version of the URN as submitted to constructor. */
  String asString

  // colon-delimited top-level components:
  /** Abbreviation of the CITE Namespace.  In a CITE-aware environment,
   * this abbreviation can be expanded to a full URI. */
  String ns

  /** The entire object component of the URN. */
  String objectComponent

  // period-delimited parts of objectComponent:
  /** Identifier for the CITE Collection */
  String collection
  /** Identifier for the individual object. */
  String objectId
  /** Identifier for the version of the object. */
  String objectVersion


  /** Type-specific extended reference separated by '@'
   * from either an object- or version-level URN. */
  String extendedRef


  /** Constructor using a String conforming to the
   * syntax and semantics of the CITE URN specification at
   * http://cite-architecture.github.io/citeurn_spec/
   *
   * @param A String representation of a CITE Object URN.
   */
  CiteUrn (String urnStr) {
    def components = urnStr.split(/:/)
    boolean syntaxOk = true
    if (components.size() != 4) {
      syntaxOk =  false
      throw new Exception("Bad URN syntax: #${urnStr}#")
    }

    if (components[0] != 'urn') {
      syntaxOk = false
    }
    if (components[1] != 'cite') {
      syntaxOk = false
    }
    if (syntaxOk) {
      this.asString = Normalizer.normalize(urnStr, Form.NFC)
      this.ns = components[2]
      this.objectComponent = components[3]

      String wholeRef
      def refParts = objectComponent.split(/@/)
      if (refParts.size() == 2) {
	this.extendedRef = refParts[1]
	wholeRef = refParts[0]
      } else {
	wholeRef = objectComponent
      }
      def idparts = wholeRef.split(/\./)
      switch (idparts.size()) {
      case 1:
      this.collection = idparts[0]
      break
      case 2:
      this.collection = idparts[0]
      this.objectId = idparts[1]
      break
      case 3:
      this.collection = idparts[0]
      this.objectId = idparts[1]
      this.objectVersion = idparts[2]
      break

      default :
      throw new Exception("Too many dot-separated parts in id component: ${components[3]}")
      break
      }

    } else {
      throw new Exception("Bad URN syntax: #${urnStr}#")
    }
  }


      
  /**
   * Gets the CITE URN object as a String.
   * @returns The URN as a String.
   */
  String toString() {
    return asString
  }


  /** Gets CITE Namespace component of the URN.
   * @returns The required namespace component of the URN.
   */
  String getNs() {
    return this.ns
  }


  /** Gets object component of the URN.
   * @returns The required object component of the URN.
   */
  String getObjectComponent() {
    return this.objectComponent
  }

  
  // Extract parts of object component:
  
  String getCollection() {
    return this.collection
  }

  String getObjectId() {
    return this.objectId
  }

  String getObjectVersion() {
    return this.objectVersion
  }

  String getExtendedRef() {
    return this.extendedRef
  }

  

  // tests on object component

  
  boolean hasObjectId() {
    return (this.objectId != null)
  } 

  boolean hasVersion() {
    return (this.objectVersion != null)
  }

  boolean hasExtendedRef() {
    return (this.extendedRef != null)
  } 



    
  /** Creates a CiteUrn identifying a Collection from
   * a CiteUrn at any level.
   * @param u The CiteUrn in question.
   * @returns A CiteUrn identifying a Collection.
   */
  String reduceToCollection() {
    return "urn:cite:${this.ns}:${this.collection}"
  }

}
