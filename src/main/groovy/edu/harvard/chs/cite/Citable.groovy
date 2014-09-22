package edu.harvard.chs.cite


/**
*
*/
class Citable {

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

  Citable () {
  }



  void setCiteExtensions() {
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

  void indexExtensions() {
    invertedExtensionsMap.clear()
    extensionsMap.keySet().each { k ->
      def urnList = extensionsMap[k]
      urnList.each { collection ->
	invertedExtensionsMap[collection] = k
      }
    }
  }

  def configuredExtensions() {
    return(extensionsMap.keySet())
  }


}
