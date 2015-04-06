package edu.harvard.chs.cite


/** A class representing the implementation of a CITE Collection
 * in the context of a specific CITE Collection service.
 * Abbreviations for extensions can be resolved with reference to the
 * inventory documenting the CITE Collection Service where this collection
 * is implemented.
 */
class CiteProperty {



  String propertyName

  String propertyType

  String label

  
  /** Constructor. */
  CiteProperty (String propName, String propType, String propLabel) throws Exception {
    this.propertyName = propName
    this.propertyType = propType
    this.label = propLabel
  }
  
}
