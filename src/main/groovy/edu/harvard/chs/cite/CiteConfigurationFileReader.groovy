package edu.harvard.chs.cite

class CiteConfigurationFileReader {

// THREE THINGS TO READ:
//


    /** Map of Cts Urns to corresponding CitationModel object.
    * The map should include an entry for every citable work in
    * the inventory.
    * This map can be read from a CitationConfiguration file.
    */
  // def citationModelMap = [:]
    //
    /** Map of Cts Urns to XML namespace mappings.
    * This map can be read from a CitationConfiguration file.
    */
    //def nsMapList = [:]

    /** Map of Cts Urns to corresponding value of online attribute.
    * The map should include an entry for every online version in the
    * inventory.
    * This map can be read from a CitationConfiguration file.
    */
    //def onlineMap = [:]


  /*  if (isOnline)  {
        def firstOnline = online[0]
        onlineMap[e.'@urn'] = firstOnline.'@docname'

        citationModelMap[e.'@urn'] = new CitationModel(firstOnline)
           if (debug > 1) {
                System.err.println "Make CM for " + firstOnline
                System.err.println " indexed from " + e.'@urn'
                System.err.println "Size of model map now " + citationModelMap.keySet().size()


}*/


}
