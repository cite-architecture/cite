package edu.harvard.chs.cite

/**
* A CitationTriplet is the object representation of the three pieces of information
* mapping a given level of a citation scheme onto a validating schema.  A set of CitationTriplets
* define a hierarchical citation scheme.
* CitationTriplets are composed of a labelling String, and two XPath templates 
* (that is, Strings in which '?' represent variables that can be substituted by the value of 
* one level of the citation hierarchy).   
* The scopePattern XPath template defines the ancestor XPath for a given level of the work,
* while the leafPattern XPath template defines the citation structure of the leaf node of
* this level of the citation hierarchy.
*
* TBD:  regular expressions should be pre compiled and documented.
*/	
class CitationTriplet {


    /** A human readable label for a citation triplet.
    */
    String label

    /** An XPath expression for the scope where
    *  the template pattern leafPattern applies.
    */
    String scopePattern

    /**  An XPath template defining, within the
    * ancestors defined by scopePattern, a single
    * citation node.
    */
    String leafPattern


    /**  An optional XPath template defining, within the
    * ancestors defined by scopePattern, citation (terminal) nodes
    * at a higher level of the hierarchy than the leaf level.
    */
    String terminalNodePattern = null

    /** String value to use as separator in label expressions.
    */
    String labelSeparator

    // Compile these REs statically in class

    /** Regular expression to get the whole attribute filter pattern
    * contained between square brackets []
    */
    def attrStringRegEx = /^.*\[([^\]]+).*/

    // This gets a key=value pair
    // def keyValPairRegEx = -/([^ ]+)[ ]*(=)[ ]*([^ ]+)/
      
    /** Regular Expression to extract, from a filter list, the 
    *key of the key=value pair matching '?' or "?" for value
    */
    def queryPatternVariableRegEx = /.*[@]([^ ]+)([ ]*=[ ]*['"]\?['"]).*/


    /** Regular expression to extract the name of an
    * element from an XPath expression or template..
    */
    def elemNameRegEx = /^\/?([^ \[]+).*/

    /**
    * Gets the filter expression for the leaf node property
    * by extracting everything between square brackets [].
    * @returns The filter expression.
    */
    String getLeafFilterExpression() {
	def matcher = (this.leafPattern =~ attrStringRegEx)
	return matcher[0][1]
    }

    /**
    * Gets the name of XML element containing the leaf node of 
    * the citation hierarchy.
    * @returns The name of the XML node.
    */
    String getLeafElementName() {
	def matcher = (this.leafPattern =~ elemNameRegEx)
	return matcher[0][1]
    }

    /**
    * Gets the attribute name from a query template of the form
    * key='?' from the leaf element's filter expression.
    * 
    */
    String getLeafVariableAttribute() {
	def filterExp = this.getLeafFilterExpression()
	def matcher = (filterExp =~ queryPatternVariableRegEx)
	if (matcher.matches()) {	
	    return(matcher[0][1])
	} else {
	    // serious error....
	    return null
	}
    }



    /** From an Xpath template, extracts the name of the
    * variable in the pattern " variable = '?' ".
    * @param s The XPath template to consider.
    * @returns A String with the name of the variable attribute
    * encoding citation values.
    */
    public static String extractLeafVariableAttribute(String s) {
        // learn how to compile static regexps ... !
        def queryPatternVariableRegEx = /.*[@]([^ ]+)([ ]*=[ ]*['"]\?['"]).*/
	def matcher = (s =~ queryPatternVariableRegEx)
	if (matcher.matches()) {	
	    return(matcher[0][1])
	} else {
	    // serious error....
	    return null
	}
    }


    /**
    * Finds the depth of the citation hierarchy defined by
    * this CitationTriplet.
    * @returns Number of components in the citation hierarchy.
    */
    int getDepth() {
	def labelArray =  label.split(this.labelSeparator)
	return labelArray.size()
    }

    /**
    * Creates an XPath expression to extract from a document all nodes 
    * matching this citation mapping.
    * @returns The resulting XPath expression.
    */
    String asXpath() {
	def emptyStr = ""
	return this.asXpath(emptyStr)
    }

    /**
    * Creates an XPath expression to extract from a document all nodes 
    * matching this citation mapping.  If nsPrefix is neither null nor an empty
    * string, each node in the XPath expression will be explicitly prefixed
    * with this identifier.
    * @param nsPrefix An XML namespace identifier to apply to each
    * node in the XPath expression.
    * @returns The resulting XPath expression.
    */
    String asXpath(String nsPrefix) {
	def nullStr = ""
	def scope = this.scopePattern.replaceAll(/=[ ]?\'\?\'/,nullStr)
	def leaf = this.leafPattern.replaceAll(/=[ ]?\'\?\'/, nullStr)

	if ((nsPrefix) && (nsPrefix != "")){
	    scope = scope.replaceAll(/\//, "/${nsPrefix}:")
	    leaf = leaf.replaceAll(/\//, "/${nsPrefix}:")
	}
	return "${scope}${leaf}"
    }


    /**
    * Overrides default toString() implementation with a
    * human-readable line of text.
    * @returns Text description of the object.
    */
    String toString() {
	return "${label} in scope ${scopePattern} at path ${leafPattern}"
    }


    /**
    * Creates an Xpath expression by stripping citation variable patterns
    * out of an Xpath template.
    * @returns A String with the XPath expression.
    */

    String containerXpath() {
	def emptyStr = ""
	return this.containerXpath(emptyStr)
    }
    
    /**
    * Creates an Xpath expression by stripping citation variable patterns
    * out of an Xpath template.  If nsPrefix is neither null nor an empty
    * string, each node in the XPath expression will be explicitly prefixed
    * with this identifier.
    * @param nsPrefix An XML namespace identifier to apply to each
    * node in the XPath expression.
    * @returns A String with the XPath expression.
    */
    String containerXpath(String nsPrefix) {
	def nullStr = ""
	def scope = this.scopePattern.replaceAll(/=[ ]?\'\?\'/,nullStr)
	if ((nsPrefix) && (nsPrefix != "")){
	    scope = scope.replaceAll(/\//, "/${nsPrefix}:")
	}
	return "${scope}"
    }


    /** Creates an Xpath expression by stripping citation variable patterns
    * out of a template expression for citation nodes at the level of the  
    * citation hierarchy reprsented by this triplet.
    * @return A String with the XPath expression.
    */
    String terminalNodeXpath() {
	def emptyStr = ""
	return this.terminalNodeXpath (emptyStr)
    }

    /** Creates an Xpath expression by stripping citation variable patterns
    * out of a template expression for citation nodes at the level of the  
    * citation hierarchy reprsented by this triplet. If nsPrefix is neither null 
    * nor an empty string, each node in the XPath expression will be explicitly 
    * prefixed with this identifier.
    * @param nsPrefix An XML namespace identifier to apply to each
    * node in the XPath expression.
    * @return A String with the XPath expression.
    */
    String terminalNodeXpath(String nsPrefix) {
	def nullStr = ""
	def terminalNode = this.terminalNodePattern.replaceAll(/=[ ]?\'\?\'/,nullStr)
	if ((nsPrefix) && (nsPrefix != "")){
	    terminalNode = terminalNode.replaceAll(/\//, "/${nsPrefix}:")
	}
	return "${terminalNode}"
    }


    /** Creates an Xpath expression by stripping citation variable patterns
    * out of a template expression for ancestor nodes at the level of the  
    * citation hierarchy represented by this triplet. If nsPrefix is neither null 
    * nor an empty string, each node in the XPath expression will be explicitly 
    * prefixed with this identifier.
    * @param nsPrefix An XML namespace identifier to apply to each
    * node in the XPath expression.
    * @return A String with the XPath expression.
    */
    String ancestorPath(String nsPrefix) {
	def nullStr = ""
	def scope = this.scopePattern.replaceAll(/\[[^\]]+\]/, nullStr)
	if ((nsPrefix) && (nsPrefix != "")){
	    scope = scope.replaceAll(/\//, "/${nsPrefix}:")
	}
	return "${scope}"
    }

    /** Determines if this triplet allows citable 
    * content at this level of the citation scheme.
    * @return True if citable content allowed.
    */
    boolean allowsTerminalNode() {
        return (this.terminalNodePattern != null)
    }

} // CitationTriplet class

