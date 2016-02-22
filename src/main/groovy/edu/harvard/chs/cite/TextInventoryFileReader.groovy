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

      /** Maps a URN to a language identifier
      * from information in parsed XML element.
      * @param elem Parsed groovy node for a work.
      * @returns Map of work's URN to work's lang attribute.
      */
      static LinkedHashMap languageMapping(groovy.util.Node elem) {
        LinkedHashMap lang = [:]
        elem.attributes().each { a ->
          def k = a.getKey()
          if (k instanceof groovy.xml.QName) {
            if (k.getLocalPart() == "lang") {
              lang[elem.'@urn'] = a.getValue()
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
      static ArrayList versionFromNode(groovy.util.Node versionNode, String parentUrn) {
        def labelNode = versionNode[ti.label][0]

        def online = versionNode[ti.online]
        boolean isOnline = (online.size() > 0)
        return([versionNode.'@urn',labelNode?.text(),isOnline, parentUrn])
      }
}
