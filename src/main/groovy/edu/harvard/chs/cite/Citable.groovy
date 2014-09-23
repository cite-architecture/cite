package edu.harvard.chs.cite


/**
*
*/
class Citable {

  /** Posible types of citable objects.
   */
  enum CiteType {
    TEXT, OBJECT, EXTENDED
  }


  /** A map of extension names to lists of CITE Collection
   * URN strings.
   */
  LinkedHashMap extensionsMap = [:]

  /** Inversion of extensionsMap, mapping CITE Collection
   * URN strings to extension names.
   */
  LinkedHashMap invertedExtensionsMap = [:]


  /** Object for storing either a CTS URN or
   * a CITE Object URN.
   */
  //def urn


  /** Minimal constructor. */
  Citable () {
  }

  // constructor with map of CITE extensions
  Citable (LinkedHashMap extMap) {
    this.extensionsMap = extMap
    indexExtensions()
  }



  /*
   * @param urnStr URN, as a String, of the citable object.
   * @throws Exception if urnStr is neither a CTS URN nor
   * a CITE Object URN.
   */


  def findCiteType (String urnStr) 
  throws Exception {
    try {
      CtsUrn urn= new CtsUrn(urnStr)
      return CiteType.TEXT
    } catch (Exception e) {
    }  
    
    try {
      CiteUrn urn = new CiteUrn(urnStr)
      // check for extensions...
      if (hasExtension(urn)) {
	return CiteType.EXTENDED
      } else {
	return CiteType.OBJECT
      }
    } catch (Exception e) {
    }
    throw new Exception("Citable: could not form a valid URN from ${urnStr}.")

  }

  // returns null if no extensions configured
  // for a CITE Collection
  ArrayList getExtensions(CiteUrn urn) {
    String coll = urn.getCollectionLevelUrn()
    return this.invertedExtensionsMap[coll]
  }
  
  // given a hashmap, set extensionsMap and its invers
  void setCiteExtensions(LinkedHashMap extMap ) {
    this.extensionsMap = extMap
    indexExtensions()
  }

  // "private" method
  private void indexExtensions() {
    invertedExtensionsMap.clear()
    extensionsMap.keySet().each { k ->
      ArrayList extList = []
      def urnList = extensionsMap[k]
      urnList.each { collection ->
	if (invertedExtensionsMap[collection] ) {
	  def elist = invertedExtensionsMap[collection]
	  elist.add(k)
	  invertedExtensionsMap[collection] = elist
	} else {
	  invertedExtensionsMap[collection] = [k]
	}
	//invertedExtensionsMap[collection] = k
      }
    }
  }


  // get list of strings naming extensions  
  ArrayList configuredExtensions() {
    ArrayList extensions = []
    extensionsMap.keySet().each {
      extensions.add("${it}")
    }
    return extensions
  }


  boolean hasExtension(CiteUrn urn) {
    String coll = urn.getCollectionLevelUrn()
    boolean found = false
    invertedExtensionsMap.keySet().each {
      if (it == coll) {
	found = true
      }
    }    
    return found
  }

}
