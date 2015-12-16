package edu.harvard.chs.cite

import java.text.Normalizer
import java.text.Normalizer.Form

/**
* A class representing a reference to a text passage in a logical
* hierarchical scheme, expressed in the notation of the Canonical
* Text Services URN system.  This class parses URNs expressed as
* Strings, and makes the components of the URN programmatically accessible.
*
* Note that while the automatically generated groovydoc output does not
* show get* methods for all the CtsUrn's member properties,
* Groovy  compilation automatically creates those public methods.
* See the included tests/testCtsUrn.groovy for examples.
*
*/
class CtsUrn {

  Integer debug = 0

  /** Version description String. */
  final versionInfo = "Part of the CITE library complying with v. 5.0 of the CTS URN specification."


  // All member properties are initialized in constructor.

  /** String version of entire URN as submitted to constructor
   * (so not enforcing URI encoding of any subref or range components).
   */
  String rawString

  // colon-delimited top-level components:
  /** Abbreviation of the CTS Namespace.  In a CTS-aware environment,
   * this abbreviation can be expanded to a full URI. */
  String ctsNamespace
  /** The entire work component of the URN. */
  String workComponent
  /** The entire passage component of the URN. */
  String passageComponent

  // period-delimited parts of workComponent:
  /** Identifier for the TextGroup.  */
  String textGroup
  /** Identifier for the notional work, corresponding to
   * 'work' in the FRBR model. */
  String work
  /** The version-level component representing an edition or
   * translation identifier. */
  String version
  /** Identifier for an exemplar. */
  String exemplar


  /** Depth of the work component's hierarchy */
  WorkLevel workLevel

  // parts of passageComponent
  /** For a single node reference, the passage */
  String passageNode
  /** For a range reference, the first node */
  String rangeBegin
  /** For a range reference, the last node */
  String rangeEnd



  // subreference values: node URN
  /** Substring reference on a node URN.   */
  String subref = null
  /** Optional index on substring */
  Integer subrefIdx = null
  //range URNs:
  /** Substring reference on a node URN, or substring reference
   * on first node of a range URN. */
  String subref1 = null
  /** Optional index on first substring */
  Integer subrefIdx1 = null
  /** Substring reference on second node of a range URN. */
  String subref2 = null
  /** Optional index on second substring */
  Integer subrefIdx2 = null


  // NEEDS DEFINITION
  //int passageLevel

  /** CTS hierarchy of works. */
  enum WorkLevel {
    EXEMPLAR, VERSION, WORK, GROUP
  }



  CtsUrn (String urnStr, Integer setDebug) {
    this.debug = setDebug
    if (debug > 3) {
      System.err.println "From source string ${urnStr}, constructing normalized..."
    }
    // Normalized to NFC, per URN spec:
    rawString = Normalizer.normalize(urnStr, Form.NFC)
    try {
      this.initializeUrn(this.rawString)
    } catch (Exception e) {
      throw e
    }
  }

  /** CtsUrns are constructed from a String conforming to the
   * syntax and semantics of the CTS URN specification.
   * @throws Exception if urnStr is not a syntactically valid CTS URN.
   */
  CtsUrn (String urnStr) {
    // reverse any URL encoding prior to parsing:
    //this.rawString = URLDecoder.decode(urnStr)


    if (debug > 3) {
      System.err.println "From source string ${urnStr}, constructing normalized..."
    }
    // Normalized to NFC, per URN spec:
    rawString = Normalizer.normalize(urnStr, Form.NFC)
    try {
      this.initializeUrn(this.rawString)
    } catch (Exception e) {
      throw e
    }
  }





  /** Gets first string on a point URN.
   * @throws Exception if subreference string does not exist or is empty.
   */
  String getSubref()
  throws Exception {
    if (this.subref) {
      return this.subref
    } else {
      throw new Exception("CtsUrn:getSubref: no valid subref in ${this.rawString}")
    }
  }

  /** Finds index, if any, of subreference.
   * @returns Integer value of subrefIdx, or default value of 1.
   * @throws Exception if subref is not defined.
   */
  Integer getSubrefIdx()
  throws Exception {
    if ((this.subref == null) || (this.subref == "")) {
      throw new Exception("CtsUrn: cannot index null subreference (raw string: ${rawString}).")

    } else {
      if (this.subrefIdx == null) {
	return 1
      } else {
	return this.subrefIdx
      }
    }
  }


  /** Finds index, if any, of first subreference.
   * @returns Integer value of subrefIdx1, or default
   * value of 1.
   * @throws Exception if subref1 is not defined.
   */
  Integer getSubrefIdx1() {
    if ((this.subref1 == null) || (this.subref1 == "")) {
      throw new Exception("CtsUrn: cannot index null subreference (raw string: ${rawString}.")

    } else {
      if (this.subrefIdx1 == null) {
	return 1
      } else {
	return this.subrefIdx1
      }
    }
  }


  /** Finds index of first subreference.
   * @returns Integer value of subrefIdx1, or default
   * value of 1.
   * @throws Exception if subref1 is not defined.
   */
  Integer getSubrefIdx2() {
    if ((this.subref2 == null) || (this.subref2 == "")) {
      throw new Exception("CtsUrn: cannot index null subreference (raw string: ${rawString}.")

    } else {
      if (this.subrefIdx2 == null) {
	return 1
      } else {
	return this.subrefIdx2
      }
    }
  }


  /** Gets workLevel property.
   * @returns WorkLevel for this URN.
   */
  WorkLevel getWorkLevel()
  throws Exception {
    if (this.workLevel != null) {
      return this.workLevel
    } else {
      throw new Exception("CtsUrn: no work component in this urn (raw string: ${rawString}).")
    }
  }


  /**
   * Private method assigns appropriate values to member properties
   * based on Urn String submitted to constructor.
   * @param urnString The values to assign, represented as a CTS Urn String.
   * @throws Exception if urnString is not a syntactically valid CTS URN.
   */
  private void initializeUrn(String urnString)
  throws Exception {
    def components = urnString.split(":")
    if ((components[0] != 'urn') ||  (components[1] != 'cts') ) {
      throw new Exception("InitializeUrn: Bad URN syntax: ${urnString}")
    }

    // exploit fall through: assign top-level,
    // colon-separated components:
    switch (components.size()) {
    case 5:
    this.passageComponent = components[4]
    case 4:
    if (urnString[-1..-1] == ":") {
      if (debug > 1) {System.err.println "\n\nTRAILING COLON\n\n"}
    } else {
      if (components.size() ==  4) {
	throw new Exception("Bad URN syntax: too few components (${components.size()} from ${components}) in ${urnString}")
      }
    }
    if (components[3]) {
      this.workComponent = components[3]
      this.ctsNamespace = components[2]

    } else {
      throw new Exception("Bad URN syntax: too few components in ${urnString}")
    }
    break

    // must have at least a namespace + work identifier,
    // in addition to required 'urn' prefix and namespace
    // identifier:
    default :
    throw new Exception("Method initializeURN: bad syntax in ${urnString}, too few components (${components.size()} from ${components})")
    break
    }


    // further split work component into
    // period-separated parts:
    def splitWork = this.workComponent.split("[\\.]")

    switch (splitWork.size()) {
    case 1:
    this.workLevel = WorkLevel.GROUP
    break

    case 2:
    this.workLevel = WorkLevel.WORK
    break

    case 3:
    this.workLevel = WorkLevel.VERSION
    break

    case 4:
    this.workLevel = WorkLevel.EXEMPLAR
    break

    default:
    break
    }

    // allow fall-through:
    switch (this.workLevel) {
    case WorkLevel.EXEMPLAR:
    this.exemplar = splitWork[3]

    case WorkLevel.VERSION:
    this.version = splitWork[2]

    case WorkLevel.WORK:
    this.work = splitWork[1]

    default :
    this.textGroup = splitWork[0]
    break
    }


    // check for range in passage component:
    if (this.passageComponent) {
      def splitRange = this.passageComponent.split("[\\-]")
      switch (splitRange.size()) {

      case 2:
      try {
	initializeRange(splitRange[0], splitRange[1])
      } catch (Exception e) {
	throw e
      }
      break

      case 1:
      if (this.passageComponent.contains('-')) {
	throw new Exception("CtsUrn, method initializeURN: bad syntax: ${urnString} has empty range component.")

      }else {
	try {
	  initializePoint(splitRange[0])
	} catch (Exception e) {
	  throw e
	}
      }
      break

      default :
      break
      }
    }
  }


  /** Gets first subreference string on a range URN.
   * @throws Exception if first subreference string does not exist or is empty.
   */
  String getSubref1() {
    if ((this.subref1) && (this.subref1 != '')) {
      return subref1
    } else {
      throw new Exception("CtsUrn, getSubref1: urn does not include subreference range (raw string: ${rawString}).")
    }
  }

  /** Gets subreference on second element of a range URN.
   * @returns Subreference string on second node of a passage range.
   * @throws Exception if second subreference string does not exist or is empty.
   */
  String getSubref2() {
    if ((this.subref2) && (this.subref2 != '')) {
      return subref2
    } else {
      throw new Exception("CtsUrn, getSubref2: urn does not include second subreference (raw string: ${rawString}).")
    }
  }


  // may be null
  String getPassageNode() {
    return this.passageNode
  }

  /** Gets first node reference of a range.
   * @throws Exception if URN is not a range.
   */
  String getRangeBegin() {
    if (this.isRange()) {
      return rangeBegin
    } else {
      throw new Exception("CtsUrn, getRangeBegin: urn is not a range (raw string: ${rawString}).")
    }
  }


  /** Gets second node reference of a range.
   * @throws Exception if URN is not a range.
   */
  String getRangeEnd() {
    if (this.isRange()) {
      return rangeEnd
    } else {
      throw new Exception("CtsUrn, getRangeEnd: urn is not a range (raw string: ${rawString}).")
    }
  }


  /** Determines if first node of a range URN has a sub reference.
   * @returns True if first node of range urn has a subreference.
   * @throws Exception if URN is not a range URN.
   */
  boolean hasSubref1()
  throws Exception {
    if (! isRange()) {
      throw new Exception("URN is not a range (raw string: ${rawString}).")
    }
    return ((subref1) && (subref1 != ''))
  }


  /** Determines if second node of a range URN has a sub reference.
   * @returns True if second node of range urn has a subreference.
   * @throws Exception if URN is not a range URN.
   */
  boolean hasSubref2()
  throws Exception {
    if (! isRange()) {
      throw new Exception("URN is not a range (raw string: ${rawString}).")
    }
    return ((subref2) && (subref2 != ''))
  }


  /** Determines if URN has a sub reference.
   * @returns True if urn has a subreference. For a range URN,
   * true if either node has a subreference.
   */
  boolean hasSubref() {
    if (isRange()) {
      return (hasSubref1() || hasSubref2())
    } else {
      return ((subref) && (subref != ''))
    }
  }

  /**
   * "Private" method uses a regular expression to parse
   *  a subref String into a list of either 1 or 2 elements.
   *  If there is an index value, it is the second element;
   *  the (possibly empty) substring value is the first element
   *  in the list.
   *  @param str The subref String to parse.
   *  @returns A list with the string component of the substring
   *  reference, and (if included) an integer index in the second
   *  element of the list.
   *  @throws SHOULD THROW A CTS EXCEPTION IF INDEX VALUE
   * DOES NOT PARSE AS A POSITIVE INTEGER:  NOT YET IMPLEMENTED.
   */
  private ArrayList indexSubref(String str) {
    ArrayList idx = []
    def substrRE = /(.*)\Q[\E(.+)\Q]\E/
    def matcher = (str =~ substrRE)
    if (matcher.matches()) {
      idx << matcher[0][1]
      idx << matcher[0][2]
    } else {
      idx << str
    }
    return idx
  }


  /**
   * "Private" method assigns appropriate values to member
   * properites if URN is a reference to a single node.
   * @param str The URN to parse, as a String.
   * @throws Exception if indexed subreference not indexed with
   * an integer.
   */
  private void initializePoint(String str)
  throws Exception {
    def splitSub = str.split(/@/)
    if (debug > 0) {
      System.err.println "INIT PT: ${str} yields " + splitSub
    }

    switch (splitSub.size()) {

    case 1:
    if (str.contains("@")) {
      throw new Exception("CtsUrn:initializePoint: empty subreference ${str}")
    }
    this.passageNode = splitSub[0]
    if (debug > 0) {
      System.err.println "Assigned passageNode: " + this.passageNode
    }
    break

    case 2:
    this.passageNode = splitSub[0]

    ArrayList subrefParts = indexSubref(splitSub[1])

    if (debug > 0) {
      System.err.println "On subref URN ${str},subref parts = " + subrefParts
    }

    this.subref = subrefParts[0]
    if (subrefParts.size() == 2) {
      try {
	this.subrefIdx = subrefParts[1].toInteger()
	if (this.subrefIdx < 1) {
	  throw new Exception("CtsUrn:initializeRange: invalid substring index ${this.subrefIdx}")
	}
      } catch (Exception e) {
	throw e
      }
    }
    break

    }

  }


  /**
   * "Private" method assigns appropriate values ot member
   * properites if URN is a reference to a range.
   * @param str The URN to parse, as a String.
   * @throws Exception if index is not an integer value, or if
   * subreference is invalid.
   */
  private void initializeRange(String str1, String str2)
  throws Exception {

    def splitSub = str1.split(/@/)
    switch (splitSub.size()) {
    case 1:
    if (str1.contains("@")) {
      throw new Exception("CtsUrn:initializeRange: empty subreference, ${str1}")
    }
    this.rangeBegin = splitSub[0]
    break

    case 2:
    this.rangeBegin = splitSub[0]
    this.subref1 = splitSub[1]
    ArrayList subrefParts = indexSubref(this.subref1)
    this.subref1 = subrefParts[0]
    if (subrefParts.size() == 2) {
      try {
	this.subrefIdx1 = subrefParts[1].toInteger()
	if (this.subrefIdx1 < 1) {
	  throw new Exception("CtsUrn:initializeRange: invalid substring index ${this.subrefIdx1}")
	}
      } catch (Exception e) {
	throw e
      }

    }
    break

    }


    splitSub = str2.split(/@/)
    switch (splitSub.size()) {
    case 1:
    if (str2.contains("@")) {
      throw new Exception("CtsUrn:initializeRange: empty subreference, ${str2}")
    }
    this.rangeEnd = splitSub[0]
    break

    case 2:
    this.rangeEnd = splitSub[0]
    this.subref2= splitSub[1]
    ArrayList subrefParts = indexSubref(this.subref2)
    this.subref2 = subrefParts[0]
    if (subrefParts.size() == 2) {
      try {
	this.subrefIdx2 = subrefParts[1].toInteger()
	if (this.subrefIdx2 < 1) {
	  throw new Exception("CtsUrn:initializeRange: invalid substring index ${this.subrefIdx2}")
	}
      } catch (Exception e) {
	throw e
      }
      break
    }

    }
  }

  /**
   * Returns the CTS URN object as a String in the normalized
   * string form specified by the CTS URN standard.
   * @returns The URN as a String.
   */
  String toString() {
	  String urnString
	  if (this.isRange()) {
			  urnString = this.getUrnWithoutPassage() + this.rangeBegin
				  if (this.subref1) {
					  urnString += "@" + getSubref1() + "[${getSubrefIdx1()}]"
				  }
			  urnString += "-" + this.rangeEnd
				  if (this.subref2) {
					  urnString +=  "@" + getSubref2() + "[${getSubrefIdx2()}]"
				  }

		} else {
			  if (this.getPassageNode()){
					  urnString = this.getUrnWithoutPassage() + this.passageNode
					  if (this.subref) {
						  urnString +=  "@" + getSubref() + "[${getSubrefIdx()}]"
					  }
			  } else {
				  urnString = this.getUrnWithoutPassage()
			  }
	    }
		return urnString
  }


/**
   * Gets the full URN for the work-level reference, without any further
   * reference information.
   * @returns The full URN, as a String, down to the work-level.
   */
  String getUrnWithoutPassage() {
    return "urn:cts:" + this.ctsNamespace + ":" + this.workComponent + ":"
  }


  /**
   * Returns the URN value with work component reduced to a notional
   * work level.
   * @returns The reduced URN, as a String.
   * @throws Exception if notional work level is not defined.
   */
  String reduceToWork()
  throws Exception {

    if (this.getWorkLevel() == WorkLevel.GROUP) {
      throw new Exception("CtsUrn: no work part of this URN (raw string: ${rawString})")
    }
    String base = "urn:cts:" + this.ctsNamespace + ":" + this.getTextGroup() + "." + this.getWork()
    if (this.getPassageComponent() == null) {
      return base
    } else {
      return  base + ":" + this.getPassageComponent()
    }
  }




  /**
   * Returns the URN value with work component reduced to a version level.
   *
   * @returns The reduced URN, as a String.
   * @throws Exception if the URN is not defined at the version level.
   */
  String reduceToVersion()
  throws Exception {
    if ((this.getWorkLevel() == WorkLevel.GROUP) || (this.getWorkLevel() == WorkLevel.WORK)) {
      throw new Exception("CtsUrn: no version part of this URN (raw string ${rawString}).")
    }
    String base = "urn:cts:" + this.ctsNamespace + ":" + this.getTextGroup() + "." + this.getWork() + "." + this.getVersion()
    if (this.getPassageComponent() == null) {
      return base
    } else {
      return  base + ":" + this.getPassageComponent()
    }
  }


  /**
   * Returns the URN value without subreference, that is,
   * reduced to the citable node.
   * @returns The reduced URN, as a String.
   */
  String reduceToNode() {
    String reducedUrn = "urn:cts:${this.ctsNamespace}:${this.workComponent}:"

    if (this.isRange()) {
      reducedUrn += this.rangeBegin + "-" + this.rangeEnd
    } else {
      reducedUrn += this.passageNode
    }
    return reducedUrn
  }


    /**
    * Gets the reference component of a passage that optionally
    * may include a sub-reference component.
    *
    */
    String getRef() {
        def parts = passageComponent.split(/@/)
        return parts[0]
    }


    /**
    * Finds passage component of a URN, trimmed
    * to <code>limit</code> levels of the citation
    * hierarchy.
    * @param limit Number of levels of hierarchy to
    * include in resulting passage String.
    * @returns Requesed passage component of the URN,
    * or if limit exceeds the number of elements
    * in the passage component, returns the original URN.
    */
    String getPassage(int limit) {
	def parts = this.passageComponent.split(/@/)
        def refPart = parts[0]
        def psgVals  = refPart.split(/\./)

	StringBuffer refBuff = new StringBuffer(psgVals[0])
	if (limit > psgVals.size()) {getWorkLevel
	    return this.getPassageComponent()
	}
	def count = 1
	while (count < limit) {
	    refBuff.append("." + psgVals[count])
	    count++
	}
	return refBuff.toString()
    }

      /**
      * Gets the last (leaf-level) part of the URN's reference.
      * @return The right-most part of the passage or range reference.
      */
      String getLeafRefValue() {
        def refVals = this.passageComponent.split(/\./)
        return refVals[refVals.size() - 1]
      }


    /**
    * Counts depth of the citation hierarchy in a URN's
    * passage component.
    * @returns Number of elements in a passage reference.
    */
    int getCitationDepth() {
      def refVals
      if (isRange()) {
	refVals = this.rangeBegin.split(/\Q.\E/)
      } else {
	refVals = this.passageComponent.split(/\Q.\E/)
      }
      return refVals.size()
    }


  /**
   * Expresses the level of the work component's
   * reference with an English word.
   * @returns A label for this level of work citation.
   */
  String labelForWorkLevel()
  throws Exception {
    switch (workLevel) {
    case CtsUrn.WorkLevel.EXEMPLAR:
    return "exemplar"

    case CtsUrn.WorkLevel.VERSION:
    return "version"

    case CtsUrn.WorkLevel.WORK:
    return "work"

    case CtsUrn.WorkLevel.GROUP:
    return "group"

    default:
    System.err.println "CtsUrn: unknown type for work component of URN string ${rawString}!"
    throw new Exception("CtsUrn: unknown work level ${workLevel} for ${rawString}")
    }
  }


  /**
   * Returns the text group component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty.
   * @returns A String value for the text group.
   */
  String getTextGroup() {
    return textGroup
  }

  /**
   * Returns the text group component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty, or the
   * nsQualified parameter is false.
   * @param nsQualified True to include the CTS namespace,
   * false to omit it.
   * @returns A String value for the text group.
   */
  String getTextGroup(boolean nsQualified) {
    if ((nsQualified) && (ctsNamespace) && (ctsNamespace != "")) {
      return "${ctsNamespace }:${textGroup}"
    } else {
      return textGroup
    }
  }


  /**
   * Returns the work component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty.
   * @returns A String value for the work.
   * @throws Exception if workLevel is undefined.
   */
  String getWork()
  throws Exception {
    if (this.work != null) {
      return this.work
    } else {
      throw new Exception("CtsUrn: no work component in this urn (raw string ${rawString}).")
    }
  }




  /**
   * Returns the work component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty, or the
   * nsQualified parameter is false.
   * @param nsQualified True to include the CTS namespace,
   * false to omit it.
   * @returns A String value for the work.
   * @throws Exception if work is not defined.
   */
  String getWork(boolean nsQualified)
  throws Exception {
    if (this.work == null) {
      throw new Exception("CtsUrn: no work component in this urn (raw string ${rawString}).")
    }

    if ((nsQualified) && (ctsNamespace) && (ctsNamespace != "")) {
      return "${ctsNamespace }:${work}"
    } else {
      return work
    }
  }




  /**
   * Returns the version component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty.
   * @returns A String value for the version.
   * @throws Exception if version is undefined.
   */
  String getVersion()
  throws Exception {
    if (this.version == null) {
      throw new Exception("CtsUrn: no version component in this urn (raw string ${rawString}).")
    } else {
      return this.version
    }
  }

  /**
   * Returns the version component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty, or the
   * nsQualified parameter is false.
   * @param nsQualified True to include the CTS namespace,
   * false to omit it.
   * @returns A String value for the version.
   * @throws Exception if version is undefined.
   */
  String getVersion(boolean nsQualified)
  throws Exception {
    if (this.version == null) {
      throw new Exception("CtsUrn: no version component in this urn (raw string ${rawString}).")
    }
    if ((nsQualified) && (ctsNamespace) && (ctsNamespace != "")) {
      return "${ctsNamespace }:${version}"
    } else {
      return version
    }
  }



  /**
   * Returns the exemplar component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty.
   * @returns A String value for the exemplar.
   * @throws Exception if exemplar is not defined.
   */
  String getExemplar()
  throws Exception {
    if (this.exemplar == null) {
      throw new Exception("CtsUrn: no exemplar component in this urn (raw string ${rawString}).")
    }
    return this.exemplar
  }

  /**
   * Returns the exemplar component of the URN qualified by its
   * cts namespace, unless the cts namespace is empty, or the
   * nsQualified parameter is false.
   * @param nsQualified True to include the CTS namespace,
   * false to omit it.
   * @returns A String value for the exemplar.
   * @throws Exception if exemplar is not defined.
   */
  String getExemplar(boolean nsQualified)
  throws Exception {
    if (this.exemplar == null) {
      throw new Exception("CtsUrn: no exemplar component in this urn (raw string ${rawString}).")
    }
    if ((nsQualified) && (ctsNamespace) && (ctsNamespace != "")) {
      return "${ctsNamespace }:${exemplar}"
    } else {
      return exemplar
    }
  }



      /**
      * Gets the numeric level of a citation categorized
      * by one of the standard text labels used in this library.
      * This method is the converse of labelForWorkLevel(),
      * with the asymmetry that "version", "edition" and "translation"
      * all correctly resolve to the same level, since edition and
      * translation can only be distinguished with further information
      * such as that supplied by a TextInventory.
      * @param label A standard String value for the work level of
      * this URN.
      * @returns An integer, ranging from 1 for text group to 4 for
      * exemplar.
      */
      static WorkLevel levelForLabel(String label) {
      	      switch(label){
	      case "group":
	      return WorkLevel.GROUP

	      case "work":
	      return WorkLevel.WORK

	      // can only be disambiguated by a text inventory,
	      // but these are the standard CTS terms:
	      case "version":
	      case "edition":
	      case "translation":
	      return WorkLevel.VERSION

	      case "exemplar":
	      return WorkLevel.EXEMPLAR

	      default:
	      return null
	      }
      }


  /**
   * Determines if the URN refers to a range of citation nodes,
   * or a single citation node.
   * @returns True if URN refers to a range of citation nodes.
   */
  boolean isRange() {
    return (this.rangeBegin != null)
  }


    /**
    * Trims the passage component to a
    * specified level of the reference hierarchy.
    * @param level The number of levels of the citation
    * hierarchy to include.
    * @returns The trimmed URN, as a String.
    */
    public String trimPassage(int level) {
      return this.getUrnWithoutPassage() + this.getPassage(level)
    }


  /** Gets information about this version of the library.
   * @return Version info string.
   */
  public String getVersionInfo() {
    return this.versionInfo
  }


  String reportAll() {
    return """
rawString ${rawString}
ctsNamespace ${ctsNamespace}

workComponent ${workComponent}
passageComponent ${passageComponent}

textGroup ${textGroup}
work ${work}
version ${version}
exemplar ${exemplar}

workLevel ${workLevel}

passageNode ${passageNode}
subref ${subref}
subrefIdx ${subrefIdx}

rangeBegin ${rangeBegin}
subref1 ${subref1}
subrefIdx1 ${subrefIdx}

rangeEnd ${rangeEnd}
subref2 ${subref2}
subrefIdx2 ${subrefIdx2}
"""
  }


  /** Creates a String representation of the URN with any subreferences
  * URL encoded.
  */
  String encodeSubref() {
    String encodedString
    if (this.isRange()) {
      encodedString = this.getUrnWithoutPassage() + this.rangeBegin //+ "-" + this.rangeEnd
      if (this.subref1) {
			encodedString += URLEncoder.encode("@${getSubref1()}[${getSubrefIdx1()}]", "UTF-8")
      }
      encodedString += "-" + this.rangeEnd
      if (this.subref2) {
			encodedString += URLEncoder.encode("@${getSubref2()}[${getSubrefIdx2()}]", "UTF-8")
      }

    } else {
		if (this.subref){
			encodedString = this.getUrnWithoutPassage() + this.passageNode
			encodedString += URLEncoder.encode("@${getSubref()}[${getSubrefIdx()}]", "UTF-8")
		} else if (this.getPassageNode()) {
			encodedString = this.getUrnWithoutPassage() + this.passageNode
		} else {
			encodedString = this
		}
    }
    return encodedString
  }
}
