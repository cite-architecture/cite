package edu.harvard.chs.cite

/**
*/
class TextInventoryXmlVerifier {


  /** Verifies that all URNs for works and versions
   * agree with their parent element.  Adds a one-line text
   * description of each error found to errorList.
   * @param root Parsed root element of the inventory.
   */
  static ArrayList checkUrnHierarchy(groovy.util.Node root) {
    ArrayList errorList = []

    //1.urns must inherit hierarchy
    root[ti.textgroup].each { tg ->
      String currentNs = ""
      String currentTg = ""
      String currentWk = ""
      String currentVers = ""

      CtsUrn tgUrn = new CtsUrn(tg.'@urn')
      currentNs = tgUrn.getCtsNamespace()
      currentTg = tgUrn.getTextGroup()
      tg[ti.work].each { w ->
	CtsUrn wkUrn = new CtsUrn(w.'@urn')
	if (wkUrn.getCtsNamespace() != currentNs) {
	  this.errorList.add("Work urn ${wkUrn} in wrong CTS namespace.")
	}
	if (wkUrn.getTextGroup() != currentTg ) {
	  this.errorList.add("Work urn ${wkUrn} in wrong textgroup.")
	}
	currentWk = wkUrn.getWork()

	w[ti.edition].each { ed ->
	  CtsUrn edUrn = new CtsUrn(ed.'@urn')
	  if (edUrn.getCtsNamespace() != currentNs) {
	    this.errorList.add("Edition urn ${edUrn} in wrong CTS namespace.")
	  }
	  if (edUrn.getTextGroup() != currentTg ) {
	    this.errorList.add("Edition urn ${edUrn} in wrong textgroup.")
	  }
	  if (edUrn.getWork() != currentWk ) {
	    this.errorList.add("Edition urn ${edUrn} in wrong work.")
	  }
	}

        w[ti.translation].each { tr ->
	  CtsUrn trUrn = new CtsUrn(tr.'@urn')
	  if (trUrn.getCtsNamespace() != currentNs) {
	    this.errorList.add("Translation urn ${edUrn} in wrong CTS namespace.")
	  }
	  if (trUrn.getTextGroup() != currentTg ) {
	    this.errorList.add("Translation urn ${trUrn} in wrong textgroup.")
	  }
	  if (trUrn.getWork() != currentWk ) {
	    this.errorList.add("Translation urn ${trUrn} in wrong work.")
	  }
	}
      }
    }
    return errorList
  }

  /** Verifies that all textgroups belong to a CTS
   * namespace declared by a ctsnamespace element in the
   * XML representation of a TextInventory.  Adds a one-line
   * description of each error found to errorList.
   * @param root Parsed root element of the inventory.
   */
  static ArrayList checkCtsNsDecl(groovy.util.Node root) {
    ArrayList errorList = []
    
    def declaredAbbrs = []
    root[ti.ctsnamespace].each {
      declaredAbbrs.add(it.'@abbr')
    }
    root[ti.textgroup].each { tg ->
      CtsUrn urn = new CtsUrn(tg.'@urn')
      if (! declaredAbbrs.containsAll(urn.getCtsNamespace())) {
	errorList.add("Undeclared cts namespace in urn ${urn}")
      }
    }
    return errorList
  }


  /** Verifies that values of xml:lang for translation
   * elements is not identical to the xml:lang value of the
   * parent work.
   */
  static ArrayList checkLangAttrs(groovy.util.Node root) {
    ArrayList errorList = []
    root[ti.textgroup][ti.work].each { w ->
      String workLang
      w.attributes().each { a ->
	def k = a.getKey()
	if (k instanceof groovy.xml.QName) {
	  if (k.getLocalPart() == "lang") {
	    workLang = a.getValue()
	  }
	}
      }
      w[ti.translation].each { t ->
	String xLang
	t.attributes().each { a ->
	  def k = a.getKey()
	  if (k instanceof groovy.xml.QName) {
	    if (k.getLocalPart() == "lang") {
	      xLang = a.getValue()
	    }
	  }
	}
	if (xLang == workLang) {
	  errorList.add("Invalid language value for translation ${t.'@urn'}: same as language for work.")
	}

      }
    }
    return errorList
  }

  /** Verifies that all XML namespace abbreviations appearing in the
   * TextInventory's citation node structure for a given edition are in a
   * list of valid XML namespace abbreviations.
   * @param citeNode The citation node element for a given edition, as a parsed
   * groovy.util.Node.
   * @param validList A list of Strings giving all XML namespace abbreviations defined
   * for the given edition.
   * @param urn The CTS URN identifying the edition in question.
   */
  static ArrayList checkXPath(groovy.util.Node citeNode, java.util.ArrayList validList, String urn) {
    ArrayList errorList = []
    citeNode.'@scope'.split('/').each { chunk ->
      def nsParts = chunk.split(/:/)
      if (nsParts.size() > 1) {
	if (! validList.containsAll(nsParts[0])) {
	  errorList.add("For ${urn}, @scope attribute ${citeNode.'@scope'} uses undeclared XML namespace.")
	}
      }
    }

    citeNode.'@xpath'.split('/').each { chunk ->
      def nsParts = chunk.split(/:/)
      if (nsParts.size() > 1) {
	if (! validList.containsAll(nsParts[0])) {
	  errorList.add("For ${urn}, @xpath attribute ${citeNode.'@xpath'} uses undeclared XML namespace.")
	}
      }
    }

    citeNode[ti.citation].each { cn ->
      errorList << checkXPath(cn, validList, urn)
    }
    return errorList
  }



  /** For every edition in the inventory, verifies that
   * all XML namespace abbreviations appearing in XPath expressions are
   * defined in namespace definitions.
   * @param root Root node of the TextInventory, as a parsed
   * groovy.util.Node.
   */
  static ArrayList checkXmlNsUsage(groovy.util.Node root) {
    ArrayList errorList = []
    root[ti.textgroup][ti.work][ti.edition].each { e ->
      String urn = e.'@urn'
      e[ti.online].each { onlineNode ->
	def definedList = []
	onlineNode[ti.namespaceMapping].each {
	  definedList.add(it.'@abbreviation')
	}
	onlineNode[ti.citationMapping][ti.citation].each { c ->
	  errorList << checkXPath(c, definedList, urn)
	}
      }
    }
    return errorList
  }



  /** Verifies that data values in the inventory comply with a number
   * of constraints of the CTS specification.  These include:
   * <ul>
   * <li>All URNs must be properly placed in their inherited hierarchy</li>
   * <li>A CTS namespace must be declared for all text groups</li>
   * <li>xml:lang attributes should be 3-char abbrs and xml:lang for
   * translations should not be equal to the xml:lang value for the notional work</li>
   * <li>recursively checks the XPath values of scope and xpath attributes on all citationMappings
   * and verifies that all XML namespaces are declared.</li>
   * </ul>
   */
  static ArrayList checkDataValues(groovy.util.Node root)
    throws Exception {
      errorList ArrayList = []
      errorList << checkUrnHierarchy(root)
      errorList << checkCtsNsDecl(root)
      errorList << checkLangAttrs(root)
      errorList << checkXmlNsUsage(root)

      //if (this.errorList.size() > 0) {
      //	throw new Exception(this.errorListToText())
      //}
      return errorList
    }

}
