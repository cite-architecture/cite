package edu.harvard.chs.cite

/**
 *  A utility class for use within the cite library to represent a
 *  citation model for a given work.  A CitationModel maps
 *  one or more abstract citation schemes onto the elements of a (single) 
 *  validating  schema using one CitationTriplet for each level of
 *  each citation scheme's  hierarchy.  This implements
 *  CTS's support for multiple citation schemes in a single work
 *  with an ordered array of CitationTriplet objects. Sequentially applying 
 * the CitationTriplets' <code>scopePattern</code> property processes 
 * all citable elements in the document, in document order.
 */
class CitationModel {

    /** The CTS TextIventory namespace  */
    final groovy.xml.Namespace ti = new groovy.xml.Namespace("http://chs.harvard.edu/xmlns/cts/ti")



    /**
    * Array of citation triplets defining the mapping of the
    * abstract citation scheme onto the schema identified by
    * schemaIdent
    */
    ArrayList mappings = []


    /** Configurable value for use in path-like display of label information.
    */
    String labelSeparator = "/"


    /**
    * Constructor creating a CitationModel object from the parsed citation node
    * of a TextInventory.
    * @param citationNode The TextInventory's citation node, parsed as a groovy.util.Node.
    */
    CitationModel (groovy.util.Node onlineNode) {

        def allSchemes = onlineNode[ti.citationMapping][0].children().findAll {
            it.name().getLocalPart() == "citation"
        }

        allSchemes.each { hierarchy ->
            def mapping = []
            this.mapHierarchy(hierarchy, mapping)
            this.mappings << mapping

        }
      }


    /**
    * Finds the "shallowest" citation scheme in the model.
    * @returns Minimum depth of all citation schemes in the model.
    */
    int minLeafDepth() {
	def min = null
	mappings.each {
	    def matches = it.scopePattern =~ /\?/
	    def currCount = matches.getCount() + 1
	    if ((!min) || (currCount < min)) {
		min = currCount
	    }
	}
	return min
    }


    /**
    * Finds the "deepest" citation scheme in the model.
    * @returns Maximum depth of all citation schemes in the model.
    */
    int getMaxLeafDepth() {
	def max = null
	mappings.each {
	    def matches = it.scopePattern =~ /\?/
	    def currCount = matches.getCount() + 1
	    if ((!max) || (currCount < max)) {
		max = currCount
	    }
	}
	return max
    }


    /** Finds the depth of a citation model at a given index.
    * @param mapIndex Index value indicating in the array of mappings
    * for a work which one we want to determine the depth for.
    * @returns Number of levels of depth.
    */
    int getDepth(int mapIndex) {
        def matches = mappings[mapIndex].scopePattern =~ /\?/
	return matches.getCount() + 1
    }


    /**
    * Gets an ordered ArrayList of Xpath expressions for all
    * CitationTriplets in this model.  Applying all of 
    * these Xpaths to the original document is equivalent
    * to selecting all citable nodes of the original document
    * in document order.
    * @returns An ArrayList of Xpath Strings.
    */
    ArrayList getXpathList() {
      def xpList = []
      mappings.each { map ->
	  CitationTriplet triple = map[map.size() - 1]
          xpList <<  triple.asXpath()
      }
      return xpList
    }



    /**
    * Gets an ordered ArrayList of Xpath expressions for all
    * CitationTriplets in this model.  Applying all of 
    * these Xpaths to the original document is equivalent
    * to selecting all citable nodes of the original document
    * in document order.
    * @returns An ArrayList of Xpath Strings.
    */
    ArrayList getXpathList(int level) {
      def xpList = []
      mappings.each { map ->
	  CitationTriplet triple = map[level]
          xpList <<  triple.asXpath()
	}
	return xpList
      }



      /*
      * PLANNED METHOD NOT YET IMPLEMENTED
      String xPathForUrn(CtsUrn urn) {
      }
      */


      /**
      * Populates a given ArrayList with CitationTriplet objects
      * representing the information described by the given
      * citation node of a TextInventory, as a Groovy Node object.
      * @param hierarchy A citation node from an XML TextInventory,
      * as a Groovy Node object.
      * @param mapList The list to populate with CitationTriplets.
      */
      void mapHierarchy(groovy.util.Node hierarchy, ArrayList mapList) {
      	   if (hierarchy.name().getLocalPart() == "citation") {

	     CitationTriplet triple
              if (hierarchy.'@leafxpath') {
             triple = new CitationTriplet(label : hierarchy."@label", leafPattern : hierarchy."@xpath", scopePattern : hierarchy."@scope", labelSeparator : this.labelSeparator, terminalNodePattern : hierarchy."@leafxpath")
             } else {
             triple = new CitationTriplet(label : hierarchy."@label", leafPattern : hierarchy."@xpath", scopePattern : hierarchy."@scope", labelSeparator : this.labelSeparator)
             }



	     mapList << triple 
	     hierarchy.children().each { 
	     	mapHierarchy(it, mapList)
	     }
	   } else { System.err.println "mapHierarchy:  did not recognize NODE NAME = " + hierarchy.name() }
      }


      /**
      * Generates human-readable String with labels for
      * each citation component formatted in a 
      * path-like String.
      * @return Label-style String.
      */
      String asLabel() {
      	     String finalLabel = ""
      	     mappings.eachWithIndex { mapping, idxNum ->
      	     String label = ""
      	     mapping.eachWithIndex  { triplet, count ->
	     		   label += triplet.label
			   if (count != mapping.size() - 1) {
			      label += this.labelSeparator
			   }
	     }
	     finalLabel += "${idxNum + 1}.\t${label}\n"
	     }
	     return finalLabel
      }



      /**
      * Overrides default toString() method with a 
      * text-formatted display of CitationTriplets.
      * @returns Text description of the object.
      */
      String toString() {
      	     def finalString = ""
	     mappings.eachWithIndex { mapping, mapCount ->
      	     def asString = ""
      	     mapping.eachWithIndex { map, count ->
	     	          def indents = count
	     		   while (indents > 0) {
			   asString += "\t"
			   indents--
			   }
	     		   asString +=   map.toString() + "\n"
                      }
      	     finalString += "${mapCount + 1}. ${asString}\n"
                  }
	     return finalString
      }

} // class CitationModel
