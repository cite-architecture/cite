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

  Integer debug = 0

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



  /** Initializes values for all constituent parts of
   * of the URN's object component.  It checks for the presence of ranges,
   * and for each object (individual object or end points of a range),
   * it checks for presence of versions and extended references.
   * @param objStr The identifying String, optionally including
   * extended reference, for either a single object or a range of
   * objects. Does NOT include collection-ID!
   */
  void initializeObjPart(String objStr)
  throws Exception {
    // first, check for range, then within each
    // obj ref, check for extended ref
    def rangeParts = objStr.split("-")

    def tempMap = [:] 
	  
    if (rangeParts.size() == 1){ // Not a range
      tempMap = parseObject(rangeParts[0])			
      this.extendedRef = tempMap["objectRef"]
      this.objectId = tempMap["objectId"]
      this.objectVersion = tempMap["objectVersion"]

      if ((this.objectVersion == null) && (this.extendedRef != null)) {
	throw new Exception("CiteUrn: illegal URN. Cannot have extended reference on object-level URN.")
      }
      
    } else if (rangeParts.size() == 2) { // A range
      // Deal with Start
      tempMap = parseObject(rangeParts[0])			
      this.extendedRef_1 = tempMap["objectRef"]
      this.objectId_1 = tempMap["objectId"]
      this.objectVersion_1 = tempMap["objectVersion"]
      
      // Deal with end
      tempMap = parseObject(rangeParts[1])			
      this.extendedRef_2 = tempMap["objectRef"]
      this.objectId_2 = tempMap["objectId"]
      this.objectVersion_2 = tempMap["objectVersion"]

      // Let's sort out versions and ranges.
      //		- Both sides can be object-only.
      //		- If only one end has a version, give the other end the same version.
      //		- If there are two versions, but different, throw and error.
      if ( (this.objectVersion_1 == null) && (this.objectVersion_2 != null)){
	this.objectVersion_1 = this.objectVersion_2
      }
      if ( (this.objectVersion_1 != null) && (this.objectVersion_2 == null)){
	this.objectVersion_2 = this.objectVersion_1
      }
      if ( (this.objectVersion_1 != null) && (this.objectVersion_2 != null) && (this.objectVersion_1 != this.objectVersion_2)){
	throw new Exception("Bad syntax in range. Both ends must identify the same version: #${objStr}#")
      }
      
    } else {
      throw new Exception("Bad syntax in object-identifier (too many hyphens): #${objStr}#")
    }
    
  }


  /** Initializes values for all constituent parts of
   * of the URN's version component.  It checks for the presence of ranges,
   * and for each version (individual version or end points of a range),
   * it checks for presence of extended references.
   * @param versionStr The identifying String, optionally including
   * extended reference, for either a single version reference or a range of
   * version references; the String should be prefixed with the object
   * identifying String, separated by a period, e.g., "object.version".
   */
  void initializeVersionPart(String versionStr) {
    // first, check for range, then within each
    // obj ref, check for extended ref
    if (debug > 1) {
      System.err.println "is it a range?"
    }


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


  /** Method to parse the parts of an Object identifier:
   * (1) object, (2) version, (3) subref
   * We need to do this for single-object URNs, and each end of a range, so
   * it makes sense to break it out.
   * NOTE: Strip off the collection-ID before sending in the Object String.
   *       Otherwise there is no way to distinguish 'collection.object' from 'object.version'
   *
   * @param A String representing an object-identifier
   * @returns Map. om.objectId om.objectVersion om.objectRef 
   **/
  Map parseObject(String objStr){
    def tempObjPlusVersion = ""
    def om = [:]
    om["objectId"] = null
    om["objectVersion"] = null
    om["objectRef"] = null

    // 1. split off extendedRef, if any
    if (objStr.contains("@")){
      om["objectRef"] = objStr.split("@")[1]
      tempObjPlusVersion = objStr.split("@")[0]
    } else {
      tempObjPlusVersion = objStr
    }

    // 2. split off version, if any
    if (tempObjPlusVersion.split(/\./).size() > 2){
      throw new Exception("Bad syntax in object-identifier (too many components): #${objStr}#")
    }
			
    if (tempObjPlusVersion.split(/\./).size() == 1){
      om["objectId"] = tempObjPlusVersion
    } 
    if (tempObjPlusVersion.split(/\./).size() == 2){
      om["objectId"] = tempObjPlusVersion.split(/\./)[0]
      om["objectVersion"] = tempObjPlusVersion.split(/\./)[1]
    } 
    // 3. we're good
    return om
  }
  
  /** 
   * Returns the number of period-delimited elements in the Object component
   * of a URN. If it is a range, returns the level of the first element of the range.
   * @param String containing the object-component of a URN
   * @returns Integer
   **/
  Integer countLevel(String objectRef) {
    String stripped = null
    if ( objectRef.contains("-") ){
      stripped = objectRef.split("-")[0].replaceAll(/@.+/,'')
    } else {
      stripped = objectRef.replaceAll(/@.+/,'')
    }
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
    def tempStr = ""
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
      // must have at least one, a collection-id
      throw new Exception("CiteUrn: could not parse ${urnStr}")
      break
      case 1:
      // has only collection-id
      this.collection = objectComponent
      break
      default:
      // collection-Id + object-Id
      def parts = objectComponent.split(/\./)
      this.collection = parts[0]
      Integer last = parts.size() - 1
      initializeObjPart(parts[1..last].join(".")) // we've just removed the collection-ID
      // Since the details might have changed, now we re-build the object-component
      if (this.objectId != null){ // not a range
	tempStr += this.collection
	tempStr += ".${this.objectId}"
	if (this.objectVersion != null){ tempStr += ".${this.objectVersion}" }
	if (this.extendedRef != null){ tempStr += "@${this.extendedRef}" }
	this.objectComponent = tempStr
      } else { // a range
	tempStr += this.collection
	tempStr += ".${this.objectId_1}"
	if (this.objectVersion_1 != null){ tempStr += ".${this.objectVersion_1}" }
	if (this.extendedRef_1 != null){ tempStr += "@${this.extendedRef_1}" }
	tempStr += "-${this.objectId_2}"
	if (this.objectVersion_2 != null){ tempStr += ".${this.objectVersion_2}" }
	if (this.extendedRef_2 != null){ tempStr += "@${this.extendedRef_2}" }
	this.objectComponent = tempStr
      }
      break
      }
      tempStr = "urn:cite:${this.ns}:${this.objectComponent}"
      this.asString = Normalizer.normalize(tempStr, Form.NFC)
      
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

  /** Gets the entire object component MINUS THE COLLECTION of the URN.
   * This is useful for constructing ranges.
   * @returns The required object component of the URN.
   */
  String getObjectWithoutCollection() {
    String tempStr = ""
    if ((this.hasObjectId()) && (this.isRange() == false)){
      tempStr = this.getObjectId()
      if (this.hasVersion()){
	tempStr += "." + this.getObjectVersion()
      }
      if (this.hasExtendedRef()){
	tempStr += "@" + this.getExtendedRef()
      }
    }
    return tempStr
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
   * for aj notional object.
   * @retkurns The object identifier, or null if the
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
   * @returns A CiteUrn identifying a Collection.
   */
  String reduceToCollection() {
    return "urn:cite:${this.ns}:${this.collection}"
  }



  /** Creates a CiteUrn identifying a CITE Object from
   * a given CiteUrn.  If the source URN has an extended reference, OR VERSIONS,
   * it is omitted. If the URN points to a range, returns both end-objects.
   * @returns A CiteUrn identifying an Object.
   */
  String reduceToObject() {
    String reducedUrn = "urn:cite:${this.ns}:${this.collection}."
    if (this.isRange()) {
      reducedUrn += "${this.getFirstObject()}"
      reducedUrn +=      "-${this.getSecondObject()}"

    } else {
      reducedUrn += this.getObjectId()
    }
    return (reducedUrn)
  }

  /** Creates a CiteUrn identifying a VERSIONED CITE Object from
   * a given CiteUrn.  If the source URN has an extended reference
   * it is omitted. If the URN points to a range, returns both end-objects and any versions.
   * @returns A CiteUrn identifying an Object and version.
   */
  String reduceToVersion() {
    String reducedUrn = "urn:cite:${this.ns}:${this.collection}."
    if (this.isRange()) {
      reducedUrn += "${this.getFirstObject()}"
      if (this.objectVersion_1 != null) {
	reducedUrn += ".${this.objectVersion_1}"
      }
      reducedUrn +=      "-${this.getSecondObject()}"
      if (this.objectVersion_2 != null) {
	reducedUrn += ".${this.objectVersion_2}"
      }


    } else {
      reducedUrn += this.getObjectId()
      if (this.hasVersion()) {
	reducedUrn += "." + this.getObjectVersion()
      }
    }
    return (reducedUrn)
  }

  /** Returns a CITE URN for the first part of a range. If "this" is not a range, just returns "this" as a string.
   * @param URN a CITE URN.
   * @returns String. A CiteUrn identifying an Object.
   */

  String getRangeBegin(){
    String temp =  "${this.reduceToCollection()}.${this.getFirstObject()}"
    if (this.isRange() ){
      if (this.objectVersion_1 != null){ 
	temp += ".${this.objectVersion_1}"
      }
      if (this.extendedRef_1 != null){ 
	temp += "@${this.extendedRef_1}"
      }
    } else {
      temp = this.toString()
    }
    return temp
  }


  /** Returns a CITE URN for the second part of a range. If "this" is not a range, just returns "this" as a string.
   * @param URN a CITE URN.
   * @returns String. A CiteUrn identifying an Object.
   */

  String getRangeEnd(){
    String temp = "${this.reduceToCollection()}.${this.getSecondObject()}"
    if (this.isRange() ){
      if (this.objectVersion_2 != null){ 
	temp += ".${this.objectVersion_2}"
      }
      if (this.extendedRef_2 != null){ 
	temp += "@${this.extendedRef_2}"
      }
    } else {
      temp = this.toString()
    }
    return temp
  }



}
