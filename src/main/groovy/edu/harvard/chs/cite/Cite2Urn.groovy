package edu.harvard.chs.cite


import java.text.Normalizer
import java.text.Normalizer.Form

/**
* A class representing a reference to an object in a set of
* objects with shared properties, expressed in the notation of the
* CITE2 Object URN system. The set is a "collection", which mandatory
* be identified alone, as a notional collection, or given a specific  This class parses URNs expressed as Strings,
* version identifier. The CITE2 Object URN makes the components of the URN
* programattically accessible.
* Note that while the automatically generated groovydoc output does not
* show get* methods for all the CtsUrn's member properties,
* Groovy  compilation automatically creates those public methods.
* urn:cite2:hmt:msAfolio.release1:12r@extendedRef


*
*/
class Cite2Urn {

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


	// 2. period-delimited parts of objectComponent. Only
	// collection is mandatory.
	//
	/** Identifier for the CITE Collection */
	String collection

	/** Identifier for the version of the collection. */
	String collectionVersion = null

	/** The entire collection component (coll + [version]) of the URN. */
	String collectionComponent

	/** The entire object component of the URN. */
	String objectComponent

	/** Identifier for an individual object. */
	String objectId = null

	/** Identifier for the first object in a range. */
	String objectId_1 = null

	/** Identifier for the second object in a range. */
	String objectId_2 = null


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
			this.collectionVersion = tempMap["collectionVersion"]

			if ((this.collectionVersion == null) && (this.extendedRef != null)) {
				throw new Exception("Cite2Urn: illegal object component ${objStr}. Cannot have extended reference on object-level URN.")
			}

		} else if (rangeParts.size() == 2) { // A range
			// Deal with Start
			tempMap = parseObject(rangeParts[0])
			this.extendedRef_1 = tempMap["objectRef"]
			this.objectId_1 = tempMap["objectId"]
			this.collectionVersion_1 = tempMap["collectionVersion"]

			if ((this.collectionVersion_1  == null) && (this.extendedRef_1 != null)) {
				throw new Exception("Cite2Urn: illegal object component ${objStr}. Cannot have extended reference on object-level URN.")
			}




			// Deal with end
			tempMap = parseObject(rangeParts[1])
			this.extendedRef_2 = tempMap["objectRef"]
			this.objectId_2 = tempMap["objectId"]
			this.collectionVersion_2 = tempMap["collectionVersion"]

			if ((this.collectionVersion_2  == null) && (this.extendedRef_2 != null)) {
				throw new Exception("Cite2Urn: illegal object component ${objStr}. Cannot have extended reference on object-level URN.")
			}


			// Let's sort out versions and ranges.
			// - Both sides can be object-only.
			// - If only one end has a version, give the other end the same version.
			// - If there are two versions, but different, throw and error.
			if ( (this.collectionVersion_1 == null) && (this.collectionVersion_2 != null)){
				this.collectionVersion_1 = this.collectionVersion_2
			}
			if ( (this.collectionVersion_1 != null) && (this.collectionVersion_2 == null)){
				this.collectionVersion_2 = this.collectionVersion_1
			}
			if ( (this.collectionVersion_1 != null) && (this.collectionVersion_2 != null) && (this.collectionVersion_1 != this.collectionVersion_2)){
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
	this.collectionVersion_1 = refParts1[0]

	def dotParts2 = rangeParts[1].split(/\./)
	this.objectId_2 = dotParts2[0]
	String remainder2 = dotParts2[1..-1].join(".")
	def refParts2 = remainder2.split(/@/)
	if (refParts2.size() == 2) {
	this.extendedRef_2 = refParts2[1]
	}
	this.collectionVersion_2 = refParts2[0]
	break

	case 1:
	def dotParts = versionStr.split(/\./)
	this.objectId = dotParts[0]
	String remainder = dotParts[1..-1].join(".")
	def refParts = remainder.split(/@/)
	if (refParts.size() == 2) {
	this.extendedRef = refParts[1]
	}
	this.collectionVersion = refParts[0]
	break

	default:
	throw new Exception("Cite2Urn:initializeVersionPart: could not make sense of ${objStr}")
	break
	}


	}


	/** Method to parse the parts of an Object identifier:
	* (1) object,  (2) subref
	* We need to do this for single-object URNs, and each end of a range, so
	* it makes sense to break it out.
	* NOTE: Strip off the collection-ID before sending in the Object String.
	*       Otherwise there is no way to distinguish 'collection.object' from 'object.version'
	*
	* @param A String representing an object-identifier
	* @returns Map. om.objectId om.collectionVersion om.objectRef
	**/
	Map parseObject(String objStr){
		def tempObj = ""
		def om = [:]
		om["objectId"] = null
		om["objectRef"] = null

		// 1. split off extendedRef, if any
		if (objStr.contains("@")){
			om["objectRef"] = objStr.split("@")[1]
			tempObj = objStr.split("@")[0]
			} else {
				tempObj = objStr
			}

			if (tempObj.split(/\./).size() == 1){
				om["objectId"] = tempObj
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
	* syntax and semantics of the CITE2 URN specification at
	* http://cite-architecture.github.io/Cite2Urn_spec/
	*
	* @param A String representation of a CITE Object URN.
	*/
	Cite2Urn (String urnStr)
		throws Exception {
		def components = urnStr.split(/:/)
		def tempStr = ""
		try{
				if ( (components.size() < 4) || (components.size() > 5)) {
					throw new Exception("Bad URN syntax: A cite2 urn should have 4 or 5 components. #${urnStr}# has ${components.size()} ")
				}

				if ( components.size() == 4){
					if (urnStr[-1..-1] != ":"){
					throw new Exception("Bad URN syntax: If a Cite2 URN lacks an object-component, it must nevertheless have a trailing colon. ")
					}
				}

				if (components[0] != 'urn') {
					throw new Exception("A Cite2 urn must begin with 'urn': #${urnStr}# begins with ${components[0]}")
				}
				if (components[1] != 'cite2') {
					throw new Exception("A Cite2 urn must begin with 'urn:cite2': #${urnStr}# begins with urn:${components[1]}")
				}
				if (components[3].tokenize(".").size() > 2){
					throw new Exception("A Cite2 Urn's collection component may consist of at most 2 parts: colleciton and [optional] version.")

				}
				if (components.size() > 4){
					if (components[4].tokenize("-").size() > 2){
						throw new Exception("A Cite2 URN's passage component can have at most 1 hyphen, indication a range.")
					}

				}


				this.asString = Normalizer.normalize(urnStr, Form.NFC)
				this.ns = components[2]
				this.collectionComponent = components[3]

				// See about collection + [version]
				if (collectionComponent.tokenize(".").size() > 1){
						this.collection = this.collectionComponent.tokenize(".")[0]
						this.collectionVersion = this.collectionComponent.tokenize(".")[1]
				} else {
					this.collection = this.collectionComponent
					this.collectionVersion = null
				}

				if (components.size() > 4 ){
					this.objectComponent = components[4]


					// See about objectComponent

					// range…
					if (this.objectComponent.tokenize("-").size() > 1){
						this.objectId = null
						this.extendedRef = null
						String tempOC1 = this.objectComponent.tokenize("-")[0]
						String tempOC2 = this.objectComponent.tokenize("-")[1]
						if (tempOC1.contains("@")){
								this.objectId_1 = tempOC1.tokenize("@")[0]
								this.extendedRef_1 = tempOC1.tokenize("@")[1]
						} else {
							this.objectId_1 = tempOC1
						}
						if (tempOC2.contains("@")){
								this.objectId_2 = tempOC2.tokenize("@")[0]
								this.extendedRef_2 = tempOC2.split("@")[1]
						} else {
							this.objectId_2 = tempOC2
						}

					// … not range…
					} else {
						if (this.objectComponent.contains("@")){
								this.objectId = this.objectComponent.tokenize("@")[0]
								this.extendedRef = this.objectComponent.tokenize("@")[1]
						} else {
							  this.objectId = this.objectComponent
								this.extendedRef = null
						}
						this.objectId_1 = null
						this.objectId_2 = null
						this.extendedRef_1 = null
						this.extendedRef_2 = null
					}
				} else {
					this.objectComponent = null
					this.objectId = null
					this.extendedRef = null
					this.objectId_1 = null
					this.objectId_2 = null
					this.extendedRef_1 = null
					this.extendedRef_2 = null
				}

				if (this.collectionVersion == null){
					if (this.extendedRef != null){
							throw new Exception("Bad CITE2 URN syntax: extended references allowed only on version-level URNs: ${urnStr}")
					}
					if (this.extendedRef_1 != null){
							throw new Exception("Bad CITE2 URN syntax: extended references allowed only on version-level URNs: ${urnStr}")
					}
					if (this.extendedRef_2 != null){
							throw new Exception("Bad CITE2 URN syntax: extended references allowed only on version-level URNs: ${urnStr}")
					}
				}

			if (objectComponent == null){
				tempStr = "urn:cite2:${this.ns}:${this.collectionComponent}:"
			} else {
				tempStr = "urn:cite2:${this.ns}:${this.collectionComponent}:${this.objectComponent}"
			}
			this.asString = Normalizer.normalize(tempStr, Form.NFC)

		} catch(Exception e){
				throw new Exception("Cite2Urn: bad URN syntax: #${urnStr}# : ${e}")
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
	tempStr += "." + this.getcollectionVersion()
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
	String getcollectionVersion() {
	return this.collectionVersion
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
	return ( (this.collectionVersion != null) || (this.collectionVersion_1 != null) )
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

	/** Creates a Cite2Urn identifying a Collection from
	* a Cite2Urn at any level.
	* @returns A Cite2Urn identifying a Collection.
	*/
	String reduceToCollection() {
	return "urn:cite:${this.ns}:${this.collection}"
	}



	/** Creates a Cite2Urn identifying a CITE Object from
	* a given Cite2Urn.  If the source URN has an extended reference, OR VERSIONS,
	* it is omitted. If the URN points to a range, returns both end-objects.
	* @returns A Cite2Urn identifying an Object.
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

	/** Creates a Cite2Urn identifying a VERSIONED CITE Object from
	* a given Cite2Urn.  If the source URN has an extended reference
	* it is omitted. If the URN points to a range, returns both end-objects and any versions.
	* @returns A Cite2Urn identifying an Object and version.
	*/
	String reduceToVersion() {
	String reducedUrn = "urn:cite:${this.ns}:${this.collection}."
	if (this.isRange()) {
	reducedUrn += "${this.getFirstObject()}"
	if (this.collectionVersion_1 != null) {
	reducedUrn += ".${this.collectionVersion_1}"
	}
	reducedUrn +=      "-${this.getSecondObject()}"
	if (this.collectionVersion_2 != null) {
	reducedUrn += ".${this.collectionVersion_2}"
	}


	} else {
	reducedUrn += this.getObjectId()
	if (this.hasVersion()) {
	reducedUrn += "." + this.getcollectionVersion()
	}
	}
	return (reducedUrn)
	}

	/** Returns a CITE URN for the first part of a range. If "this" is not a range, just returns "this" as a string.
	* @param URN a CITE URN.
	* @returns String. A Cite2Urn identifying an Object.
	*/

	String getRangeBegin(){
	String temp =  "${this.reduceToCollection()}.${this.getFirstObject()}"
	if (this.isRange() ){
	if (this.collectionVersion_1 != null){
	temp += ".${this.collectionVersion_1}"
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
	* @returns String. A Cite2Urn identifying an Object.
	*/

	String getRangeEnd(){
		String temp = "${this.reduceToCollection()}.${this.getSecondObject()}"
		if (this.isRange() ){
			if (this.collectionVersion_2 != null){
				temp += ".${this.collectionVersion_2}"
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
