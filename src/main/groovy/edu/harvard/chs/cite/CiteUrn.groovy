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
  


  /** String version of the URN as submitted to constructor
   * normalized to Unicode KFC form.
   */
  String asString

  // 1. colon-delimited top-level components:
  //
  /** Abbreviation of the CITE Namespace.  In a CITE-aware environment,
   * this abbreviation can be expanded to a full URI. */
  String ns

  /** The entire object component of the URN. */
  String objectComponent

  // 2. period-delimited parts of objectComponent. Only 
  // collection is mandatory.
  //
  /** Identifier for the CITE Collection */
  String collection

  /** Identifier for an individual object. */
  String objectId = null

  /** Identifier for the first object in a range. */
  String objectId_1 = null

  /** Identifier for the second object in a range. */
  String objectId_2 = null

  /** Identifier for the version of the object. */
  String objectVersion = null

  /** Identifier for the first version in a range. */
  String objectVersion_1 = null

  /** Identifier for the second version in a range. */
  String objectVersion_2 = null

  /** Type-specific extended reference separated by '@'
   * from either an object- or version-level URN. */
  String extendedRef = null

  /** Type-specific extended reference separated by '@'
   * from first node in a range in either an object- or version-level URN. */
  String extendedRef_1 = null

  /** Type-specific extended reference separated by '@'
   * from second node in a range in either an object- or version-level URN. */
  String extendedRef_2 = null


  void initializeObjPart(String objStr) 
  throws Exception {
    // first, check for range, then within each
    // obj ref, check for extended ref
    def rangeParts = objStr.split("-")
    switch(rangeParts.size()) {


    case 2:
    def refParts1 = rangeParts[0].split(/@/)
    if (refParts1.size() == 2) {
      this.extendedRef_1 = refParts1[1]
    } 
    this.objectId_1 = refParts1[0]

    def refParts2 = rangeParts[1].split(/@/)
    if (refParts2.size() == 2) {
      this.extendedRef_2 = refParts2[1]
    } 
    this.objectId_2 = refParts2[0]
    break

    case 1:
    def refParts = rangeParts[0].split(/@/)
    if (refParts.size() == 2) {
      this.extendedRef = refParts[1]
    } 
    this.objectId = refParts[0]
    break

    default:
    throw new Exception("CiteUrn:initializeObjPart: could not make sense of ${objStr}")
    break
    }
  }

  void initializeVersionPart(String versionStr) {
    // first, check for range, then within each
    // obj ref, check for extended ref
    System.err.println "ANALYZE VERSION " + versionStr
    System.err.println "is it a range?"

    def rangeParts = versionStr.split("-")
    switch(rangeParts.size()) {

    case 2:
    def dotParts1 = rangeParts[0].split(/\./)
    this.objectId_1 = dotParts1[0]
    String remainder1 = dotParts1[1..-1].join(".")
    def refParts1 = remainder1.split(/@/)
    if (refParts1.size() == 2) {
      this.extendedRef_1 = refParts1[1]
    }
    this.objectVersion_1 = refParts1[0]

    def dotParts2 = rangeParts[1].split(/\./)
    this.objectId_2 = dotParts2[0]
    String remainder2 = dotParts2[1..-1].join(".")
    def refParts2 = remainder2.split(/@/)
    if (refParts2.size() == 2) {
      this.extendedRef_2 = refParts2[1]
    } 
    this.objectVersion_2 = refParts2[0]
    break

    case 1:
    def dotParts = versionStr.split(/\./)
    this.objectId = dotParts[0]
    String remainder = dotParts[1..-1].join(".")
    def refParts = remainder.split(/@/)
    if (refParts.size() == 2) {
      this.extendedRef = refParts[1]
    } 
    this.objectVersion = refParts[0]
    break

    default:
    throw new Exception("CiteUrn:initializeVersionPart: could not make sense of ${objStr}")
    break
    }


  }



  Integer countLevel(String objectRef) {
    String stripped = objectRef.replaceAll(/@.+/,'')
    return stripped.split(/\./).size()
  }

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

      switch (countLevel(objectComponent)) {
      case 0:
      throw new Exception("CiteUrn: could not parse ${urnStr}")
      break
      case 1:
      this.collection = objectComponent
      break
      case 2:
      def parts = objectComponent.split(/\./)
      this.collection = parts[0]
      Integer last = parts.size() - 1
      initializeObjPart(parts[1..last].join("."))

      break
      case 3:
      def parts = objectComponent.split(/\./)
      this.collection = parts[0]
      Integer last = parts.size() - 1
      initializeVersionPart(parts[1..last].join("."))
      break

      default :

      break
      }


    } else {
      throw new Exception("CiteUrn: bad URN syntax: #${urnStr}#")
    }
  }


      
  /**
   * Gets the CITE URN object as a String.
   * @returns The URN as a String.
   */
  String toString() {
    return asString
  }


  /** Gets the CITE Namespace component of the URN.
   * @returns The required namespace component of the URN.
   */
  String getNs() {
    return this.ns
  }


  /** Gets the entire object component of the URN.
   * @returns The required object component of the URN.
   */
  String getObjectComponent() {
    return this.objectComponent
  }

  
  // Extract parts of object component:


  /** Extracts from the object component the required identifier 
   * for a CITE Collection.
   * @returns The collection identifier.
   */
  String getCollection() {
    return this.collection
  }


  /** Extracts from the object component the optional identifier 
   * for a notional object.
   * @returns The object identifier, or null if the
   * URN only identifies a collection.
   */
  String getObjectId() {
    return this.objectId
  }

  /** Extracts from the object component the optional identifier 
   * for the version of an object.
   * @returns The version identifier, or null if the
   * URN only identifies a collection or notional object.
   */
  String getObjectVersion() {
    return this.objectVersion
  }

  /** Extracts from the object component the optional string
   * with a type-specific extended reference.
   * @returns A string with additional identifying information
   * defined by a CITE Extension, or null if none is present.
   */
  String getExtendedRef() {
    return this.extendedRef
  }

  /** Gets the object identifier for the first object
   * in a range.
   * @returns Identifier for the first object in a range,
   * or null if this URN is not a range.
   */
  String getFirstObject() {
    return this.objectId_1
  }



  /** Gets the object identifier for the last object
   * in a range.
   * @returns Identifier for the last object in a range,
   * or null if this URN is not a range.
   */
  String getSecondObject() {
    return this.objectId_2
  }


  /** Extracts from the first object in a range the optional string
   * with a type-specific extended reference.
   * @returns A string with additional identifying information
   * defined by a CITE Extension, or null if none is present.
   */
  String getFirstExtended() {
    return this.extendedRef_1
  }



  /** Extracts from the second object in a range the optional string
   * with a type-specific extended reference.
   * @returns A string with additional identifying information
   * defined by a CITE Extension, or null if none is present.
   */
  String getSecondExtended() {
    return this.extendedRef_2
  }

  

  // tests on object component

  /** Tests if the URN identifies a notional work.
   * @returns True if the URN has a work identifier.
   */  
  boolean hasObjectId() {
    return (this.objectId != null)
  } 


  /** Tests if the URN identifies a version.
   * @returns True if the URN has a version identifier.
   */  
  boolean hasVersion() {
    return (this.objectVersion != null)
  }


  /** Tests if the URN includes a type-specific extended reference.
   * @returns True if the URN has an extended reference.
   */  
  boolean hasExtendedRef() {
    return (this.extendedRef != null)
  } 
  

  /** Tests if the URN refers to a range of objects in
   * an ordered collection.
   * @returns True if the URN is a range.
   */  
  boolean isRange() {
    return ((this.objectId_1 != null) && (this.objectId_2 != null))
  }
  
  // Manipulating URNs
  
  /** Creates a CiteUrn identifying a Collection from
   * a CiteUrn at any level.
   * @param u The CiteUrn in question.
   * @returns A CiteUrn identifying a Collection.
   */
  String reduceToCollection() {
    return "urn:cite:${this.ns}:${this.collection}"
  }

}
