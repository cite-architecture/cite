package edu.harvard.chs.cite

class TextInventoryFileReader{


  /** XML namespace for the TextInventory vocabulary.    */
  static groovy.xml.Namespace ti = new groovy.xml.Namespace("http://chs.harvard.edu/xmlns/cts")



      static ArrayList collectCtsNamespaceData (groovy.util.Node root) {
        ArrayList tripleList = []
        root[ti.ctsnamespace].each { ctsns ->
          def abbr = ctsns.'@abbr'
          def uri = ctsns.'@ns'
          def descr = ctsns[ti.description][0]?.text()
          def nsTriple = [abbr, uri, descr]
          tripleList.add(nsTriple)
        }
        return tripleList
      }

      /** Creates a pairing of URN and label for a text group
      * from information in parsed textgroup element.
      * @param tgNode Parsed groovy node for a text group.
      * @returns Ordered pair of strings with URN and label values.
      */
      static ArrayList tgFromNode(groovy.util.Node tgNode) {
        def nameNode = tgNode[ti.groupname][0]
        return([tgNode.'@urn',nameNode?.text()])
      }

      /** Creates a pairing of URN and label for a text group
      * from information in parsed textgroup element.
      * @param wkNode Parsed groovy node for a work.
      * @returns Map of work's URN to work's lang attribute.
      */
      static LinkedHashMap workLangMapping(groovy.util.Node wk) {
        LinkedHashMap lang = [:]
        wk.attributes().each { a ->
          def k = a.getKey()
          if (k instanceof groovy.xml.QName) {
            if (k.getLocalPart() == "lang") {
              lang[wk.'@urn'] = a.getValue()
            }
          }
        }
        return lang
      }



      /** Creates a triple of URN, label and parent URN
      * for a work from information in a parsed work element.
      * @param wkNode Parsed groovy node for a work.
      * @param parentUrn URN, as a String, for the work's textgroup.
      * @returns Ordered triple of strings.
      */
      static ArrayList wkFromNode(groovy.util.Node wkNode, String parentUrn) {
        def titleNode = wkNode[ti.title][0]
        return([wkNode.'@urn',titleNode?.text(), parentUrn])
      }



      /** Creates a triple of URN, label and parent URN
      * for a work from information in a parsed work element.
      * @param wkNode Parsed groovy node for a work.
      * @param parentUrn URN, as a String, for the work's textgroup.
      * @returns Ordered triple of strings.
      */
      static ArrayList editionFromNode(groovy.util.Node edNode, String parentUrn) {
        def labelNode = edNode[ti.label][0]

        def online = edNode[ti.online]
        boolean isOnline = (online.size() > 0)
        return([edNode.'@urn',labelNode?.text(),isOnline, parentUrn])
      }
}
