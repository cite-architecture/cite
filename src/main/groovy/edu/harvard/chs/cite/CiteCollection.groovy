package edu.harvard.chs.cite


/** A class representing the implementation of a CITE Collection
 * in the context of a specific CITE Collection service.
 * Abbreviations for extensions can be resolved with reference to the
 * inventory documenting the CITE Collection Service where this collection
 * is implemented.
 */
class CiteCollection {


  /** CITE Collection URN for this collection,
   * as a String.
  String urn

  /** Name of the property with object's URN. */
  CiteProperty canonicalIdProp

  /** Name of the property with a human-readable label.*/
  CiteProperty labelProp

  /** Abbreviated form of CITE namespace used in URNs.*/
  String nsAbbr

  /** URI uniquely identifying the CITE namespace. */
  String nsFull

  /** Possibly empty list of extensions applicable to this 
   * Collection.  These are identified by abbreviations that 
   * can resolved with reference to the inventory of the Collection
   * Service where this Collection is instantiated. */
  ArrayList extendedBy = []


  /** List of one or more name/type pairs identifying sources
   * for this collection's  data.  */
  ArrayList sources = []


  // other properties beyond id and label ....
  ArrayList properties = []

  
  /** Constructor. */
  CiteCollection () throws Exception {
  }

  
  
}
