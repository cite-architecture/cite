package edu.harvard.chs.cite


/** A class representing the implementation of a CITE Collection
 * in the context of a specific CITE Collection service.
 * Abbreviations for extensions can be resolved with reference to the
 * inventory documenting the CITE Collection Service where this collection
 * is implemented.
 */
class CiteCollection {


  /** CITE Collection URN for this collection */
   public CiteUrn urn = null

  /** Name of the property with object's URN. */
  public CiteProperty canonicalIdProp = null

  /** Name of the property with a human-readable label.*/
  public CiteProperty labelProp = null

  /** Abbreviated form of CITE namespace used in URNs.*/
  public String nsAbbr = null

  /** URI uniquely identifying the CITE namespace. */
  public String nsFull = null

  /** List of one or more name/type pairs identifying sources
   * for this collection's  data.  */
  public ArrayList sources = []
  
  
  /** Possibly empty list of extensions applicable to this 
   * Collection.  These are identified by abbreviations that 
   * can resolved with reference to the inventory of the Collection
   * Service where this Collection is instantiated. */
  public ArrayList extendedBy = []

  // other properties beyond id and label ....
  public ArrayList properties = []

  
  /** Empty constructor. */
  CiteCollection () throws Exception {
  }

  /** Evaluates configuration of this collection.
   * @returns True if configuration is valid.
   */
  boolean isValid() {
    // check that nsFull is a URI object...
    return(urn != null
	   && (canonicalIdProp != null)
	   && (labelProp != null)
	   && (nsAbbr == urn.getNs())
	   && (nsFull != null)
	   && (sources.size() > 0)
	  )
  }


  public String getNsFull() {
    return this.nsFull
  }
  
}
