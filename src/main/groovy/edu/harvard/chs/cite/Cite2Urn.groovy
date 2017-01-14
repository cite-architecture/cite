package edu.harvard.chs.cite


import java.text.Normalizer
import java.text.Normalizer.Form

/**
* A class representing a reference to an object in a set of
* objects with shared properties, expressed in the notation of the
* CITE2 Object URN system. The set is a "collection", which may
* be identified alone, as a notional collection, or given a specific version.
* This class parses URNs expressed as Strings,
* The CITE2 Object URN makes the components of the URN
* programattically accessible.
* Note that while the automatically generated groovydoc output does not
* show get* methods for all the CtsUrn's member properties,
* Groovy  compilation automatically creates those public methods.
* E.g. urn:cite2:hmt:msAfolio.release1:12r@extendedRef
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

				if (this.objectId != null){
					if (this.objectId.contains(".")){
								throw new Exception("Bad CITE2 URN syntax: Object-id component cannot contain a '.' character: ${urnStr}")
					}
				}
				if (this.objectId_1 != null){
					if (this.objectId_1.contains(".")){
								throw new Exception("Bad CITE2 URN syntax: Object-id component cannot contain a '.' character: ${urnStr}")
					}
				}
				if (this.objectId_1 != null){
					if (this.objectId_2.contains(".")){
								throw new Exception("Bad CITE2 URN syntax: Object-id component cannot contain a '.' character: ${urnStr}")
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


	/** Extracts from the collection ID
	* for a CITE Collection.
	* @returns The collection identifier.
	*/
	String getNotionalCollection() {
		return this.collection
	}

	/** Extracts from the collection ID
	* for a CITE Collection. May be null.
	* @returns The collection identifier.
	*/
	String getCollectionVersion() {
		return this.collectionVersion
	}

	/** Returns the collection + version (if present)
	* for a CITE Collection.
	* @returns The collection identifier.
	*/
	String getCollectionComponent() {
		return this.collectionComponent
	}

	/** Gets the entire object component of the URN.
	* @returns The object component of the URN. May be null.
	*/
	String getObjectComponent() {
	return this.objectComponent
	}

	/** Extracts from the object component the optional identifier
	* for an object.
	* @returns The object identifier, or null if the
	* URN only identifies a collection, OR IS A RANGE
	*/
	String getObjectId() {
			return this.objectId
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

	/** Tests if the URN identifies a notional or versioned collection.
	* @returns True if the URN has an object identifier; returns TRUE for ranges.
	*/
	boolean hasObjectId() {
		return ((this.objectId != null) || (this.isRange()) )
	}


	/** Tests if the URN identifies a version.
	* @returns True if the URN has a version identifier.
	*/
	boolean hasCollectionVersion() {
	return (this.collectionVersion != null)
	}


	/** Tests if the URN includes a type-specific extended reference.
	* @returns True if the URN has an extended reference, or if either end of a range has one.
	*/
	boolean hasExtendedRef() {
		return ((this.extendedRef != null) || (this.extendedRef_1 != null) || (this.extendedRef_2 != null))
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
	Cite2Urn reduceToCollection() {
		return new Cite2Urn("urn:cite2:${this.ns}:${this.collection}:")
	}

	/** Creates a Cite2Urn identifying a Collection at the version-level from
	* a Cite2Urn at any level.
	* If the original URN does not have a version, returns a collection-level URN string.
	* @returns A Cite2Urn identifying a Collection and its version
	*/
	Cite2Urn reduceToCollectionVersion() {
		String returnString = ""
		if (this.collectionVersion != null){
		 	returnString = "urn:cite2:${this.ns}:${this.collection}.${this.collectionVersion}:"
		} else {
		 	returnString = "urn:cite2:${this.ns}:${this.collection}:"
		}
		return new Cite2Urn(returnString)
	}

	/** Creates a Cite2Urn at the Collection or Version level, depending on the original,
	* stripping off the object-component
	* @returns A Cite2Urn identifying a Collection and its version
	*/
	Cite2Urn getUrnWithoutObject() {
		String returnString = ""
		if (this.collectionVersion != null){
		 	returnString = "urn:cite2:${this.ns}:${this.collection}.${this.collectionVersion}:"
		} else {
		 	returnString = "urn:cite2:${this.ns}:${this.collection}:"
		}
		return new Cite2Urn(returnString)
	}



	/** Creates a Cite2Urn identifying a CITE Object from
	* a given Cite2Urn.  If the source URN has an extended reference
	* it is omitted. If the URN points to a range, returns both end-objects.
	* @returns A Cite2Urn identifying an Object.
	*/
	Cite2Urn reduceToObject() {
		String reducedUrn = "urn:cite2:${this.ns}:${this.collection}"
		if (this.collectionVersion != null){
			reducedUrn += ".${this.collectionVersion}"
		}
		reducedUrn += ":"
		if (this.isRange()) {
			reducedUrn += "${this.getFirstObject()}"
			reducedUrn += "-${this.getSecondObject()}"
		} else {
			if (this.getObjectId() != null){
				reducedUrn += this.getObjectId()
			}
		}
		return new Cite2Urn (reducedUrn)
	}


	/** Returns the first part of a range. If "this" is not a range, just returns "this" as a string.
	* @param URN a CITE URN.
	* @returns String. A Cite2Urn identifying an Object.
	*/

	String getRangeBegin(){
		String temp =  "${this.reduceToCollectionVersion()}"
		if (this.isRange() ){
			temp += "${this.objectId_1}"
			if ( this.extendedRef_1 != null){
				temp += "@${this.extendedRef_1}"
			}
		} else {
			if(this.objectComponent != null){
				temp += this.getObjectComponent()
			}
		}
		return new Cite2Urn(temp)
	}


	/** Returns a CITE URN for the second part of a range. If "this" is not a range, just returns "this" as a string.
	* @param URN a CITE URN.
	* @returns String. A Cite2Urn identifying an Object.
	*/

	String getRangeEnd(){
		String temp =  "${this.reduceToCollectionVersion()}"
		if (this.isRange() ){
			temp += "${this.objectId_2}"
			if ( this.extendedRef_2 != null){
				temp += "@${this.extendedRef_2}"
			}
		} else {
			if(this.objectComponent != null){
				temp += this.getObjectComponent()
			}
		}
		return new Cite2Urn(temp)
	}



}
