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

  
  /** Constructor. */
  CiteProperty (String propName, String propType, String propLabel) throws Exception {
    if (propType in citeTypes) {
      this.propertyName = propName
      this.propertyType = propType
      this.label = propLabel
    } else {
      throw new Exception("CiteProperty: invalid value for type, ${propType}")
    }
  }
  
}
