package edu.harvard.chs.cite


/** A class representing the implementation of a CITE Collection
 * in the context of a specific CITE Collection service.
 * Abbreviations for extensions can be resolved with reference to the
 * inventory documenting the CITE Collection Service where this collection
 * is implemented.
 */
class CiteProperty {

  /** Allowed string values for propertyType */
  Set citeTypes = ["string",
		   "number",
		   "boolean",
		   "markdown"
		   ]

  /** Identifying name of property. */
  String propertyName

  /** Type of property.  String value must be 
   * one of the set citeTypes. */
  String propertyType

  /** A human-readable name for this property.  */
  String label


  /** Possible null set defining a controlled vocabulary list for a
   * string property.  A null valueSet means that any value is allowed.
   */
  Set valueSet = []
  
  /** Constructor with three required values.
   * @param propName Name of the property.
   * @param propType One of the allowed values for property type.
   * @param propLabel Human-readable label for the property.
   */
  CiteProperty (String propName, String propType, String propLabel) throws Exception {
    if (propType in citeTypes) {
      this.propertyName = propName
      this.propertyType = propType
      this.label = propLabel
    } else {
      throw new Exception("CiteProperty: invalid value for type, ${propType}")
    }
  }


  /**
   * Constructor with controlled vocabulary list for a string property.
   * @param propName Name of the property.
   * @param propLabel Human-readable label for the property.
   * @param propValues Set of allowed values for this string property.
   */
  CiteProperty (String propName, String propLabel, Set propValues) {
    this.propertyType = "string"
    this.propertyName = propName
    this.label = propLabel
    this.valueSet = propValues
  }



  /** Gets set of controlled vocabulary for a string object.
   * If there are no restrictions on values, the Set will be empty.
   * @returns A (possibly empty) set of string values.
   * @throws Exception if the CiteProperty is not a string type property.
   */
  Set getVocabulary() 
  throws Exception {
    if (this.propertyType != "string") {
      throw new Exception("CiteProperty: cannot get controlled vocabulary on object of type ${this.propertyType}")
    } else {
      return this.valueSet
    }
  }

  /** Overrides default. */
  String toString() {
    return("${this.propertyName} (${this.propertyType})")
  }
  
}
