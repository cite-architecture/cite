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
  def urn


  /** Minimal constructor. */
  Citable () {
  }




  /** Constructor requires a citable value, either
   * a CTS URN or a CITE Collection URN, as
   * a String value.
   * @param urnStr URN, as a String, of the citable object.
   * @throws Exception if urnStr is neither a CTS URN nor
   * a CITE Object URN.
   */
  Citable (String urnStr) 
  throws Exception {
    boolean works = false
    try {
      this.urn = new CtsUrn(urnStr)
      works = true
    } catch (Exception e) {
    }  
    try {
      this.urn = new CiteUrn(urnStr)
      works = true
    } catch (Exception e) {
    }
    if (! works) {
      throw new Exception("Citable: could not form a valid URN from ${urnStr}.")
    }
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
      def urnList = extensionsMap[k]
      urnList.each { collection ->
	invertedExtensionsMap[collection] = k
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


}
