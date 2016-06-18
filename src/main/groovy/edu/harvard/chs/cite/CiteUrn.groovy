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

  System.err.println "Initialized ${objStr}. Got ${rangeParts.size()} rangeParts."
 	def tempMap = [:] 
	  
  	if (rangeParts.size() == 1){ // Not a range
		tempMap = parseObject(rangeParts[0])			
		this.extendedRef = tempMap["objectRef"]
		this.objectId = tempMap["objectId"]
		this.objectVersion = tempMap["objectVersion"]
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

	} else {
      throw new Exception("Bad syntax in object-identifier (too many hyphens): #${objStr}#")
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
			System.err.println "At this point, tempObjPlusVersion = ${tempObjPlusVersion}"
			System.err.println "size() = ${tempObjPlusVersion.split(/\./).size()}"

			// 2. split off version, if any
			if (tempObjPlusVersion.split(/\./).size() > 2){
			  throw new Exception("Bad syntax in object-identifier (too many components): #${objStr}#")
			}
			
			if (tempObjPlusVersion.split(/\./).size() == 1){
				System.err.println "Size == 1 for ${tempObjPlusVersion}"
				om["objectId"] = tempObjPlusVersion
			} 
			if (tempObjPlusVersion.split(/\./).size() == 2){
				System.err.println "Size == 2 for ${tempObjPlusVersion}"
				System.err.println "[0] = ${tempObjPlusVersion.split(/\./)[0]}"
				System.err.println "[1] = ${tempObjPlusVersion.split(/\./)[1]}"
				om["objectId"] = tempObjPlusVersion.split(/\./)[0]
				om["objectVersion"] = tempObjPlusVersion.split(/\./)[1]
			} 
			// 3. we're good
			return om
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
		System.err.println "Inside constructor. ${urnStr} yields objectComponent: ${objectComponent}"
		System.err.println "countLevel(${objectComponent}) = ${countLevel(objectComponent)}"
		switch (countLevel(objectComponent)) {
			case 0: 										// must have at least one, a collection-id
			throw new Exception("CiteUrn: could not parse ${urnStr}")
			break
			case 1:										// has only collection-id
			this.collection = objectComponent
			break
			default:										// collection-Id + object-Id
			def parts = objectComponent.split(/\./)
			this.collection = parts[0]
			Integer last = parts.size() - 1
			initializeObjPart(parts[1..last].join(".")) // we've just removed the collection-ID
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
	  System.err.println "Checking ${this} to see if it is a range."
		  System.err.println "${this.objectId_1} - ${this.objectId_2} "
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
   * a given CiteUrn.  If the source URN has an extended reference,
   * it is omitted. If the URN points to a range, returns both end-objects.
   * @returns A CiteUrn identifying an Object.
   */
  String reduceToObject() {
	  System.err.println "reduceToObject(${this})"
	  String reducedUrn = "urn:cite:${this.ns}:${this.collection}."
	  if (this.isRange()) {
		  System.err.println "${this} is a range."
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

}
