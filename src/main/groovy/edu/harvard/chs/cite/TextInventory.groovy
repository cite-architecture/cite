package edu.harvard.chs.cite

import groovy.xml.StreamingMarkupBuilder


/**
* Class modelling a TextInventory.
* Methods for finding artifacts in the TextInventory appear in two forms:  
* <ul>
* <li>an internal "private" method returning a TextInventory class data structure.
* These methods have 'Data' in their names.</li>
* <li>a public method returning URN values or String values</li>
* </ul>
* The internal 'Data' methods should not be used:  internal data structures may
* change.  The URN and String methods are stable.  They will not change their signature
* and should produce identical results even if the underlying implementation changes.
*/
class TextInventory {


    /** Debugging level. */
    Integer debug = 0

    /** List of data values that violate the TextInventory definition. */
    def errorList = []

    /** Character encoding to use when representing TextInventory as 
    * a File object.
    */
    String enc = "UTF-8"

    /** XML namespace for the TextInventory vocabulary.    */
    static groovy.xml.Namespace ti = new groovy.xml.Namespace("http://chs.harvard.edu/xmlns/cts")

    /** XML namespace for the CTS vocabulary.    */
    static groovy.xml.Namespace ctsns = new groovy.xml.Namespace("http://chs.harvard.edu/xmlns/cts")

    /** The TextInventory ID. In an XML serialization validating against the 
    * TextInventory schema,  this is the <code>tiid</code>
    * attribute on the root element. 
    */
    //def tiId

    /** The version of the TextInventory schema this inventory validates against.
    */
    def tiVersion

    /** A list of namespace triplets.  Each CTS namespace
    * is comprised of a namespace abbreviation, a full
    * URI, and a description.
    */
    def ctsnamespaces = []

    /** A list of textgroup structures.
    * Each textgroup structure is comprised of a CTS urn and a label.
    * Although the XML schema for a TextInventory allows multiple
    * labels for a textgroup, this data structure keeps only one.
    */
    def textgroups = []

    /** A list of work structures.
    * Each work structure is comprised of
    * a CTS URN, a title, and a parent (textgroup) URN.
    * (A full work-level URN can therefore be composed by joining the
    * work-level URN and the work ID).
    * Although the XML schema for a TextInventory allows multiple
    * titles for a work, this data structure keeps only one.
    */
    def works = []


    /**  A list of version-level structures.
    * Each version-level structure is comprised of
    * a CTS URN for the version, a label, a boolean flag indicating 
    * whether or not this version is online, and a parent (work) URN.
    */
    def editions = []

    /**  A list of version-level structures.
    * Each version-level structure is comprised of
    * a CTS URN for the version, a label, a boolean flag indicating 
    * whether or not this version is online, and a parent (work) URN.
    */
    def translations = []


    /** Map of Cts Urns to corresponding CitationModel object.
    * The map should include an entry for every citable work in
    * the inventory.
    */
    def citationModelMap = [:]

    /** Map of Cts Urns to XML namespace mappings.*/
    def nsMapList = [:]

    /** Map of Cts Urns to corresponding value of online attribute.
    * The map should include an entry for every online version in the 
    * inventory.
    */
    def onlineMap = [:]


    /** Map of work-level Cts Urns to ISO three-letter language codes.
    */
    def worksLanguages = [:]


    /** Map of version-level Cts Urns to ISO three-letter language codes.
    */
    def translationLanguages = [:]


    /** Generates listing of data validation errors.
    * @returns String with list of validation errors.
    */
    String errorListToText() {
        StringBuffer eList = new StringBuffer("${this.errorList.size()} invalid data values found:\n")
        this.errorList.each {
            eList.append("\t${it}\n")
        }
        return eList.toString()
    }

    /** Verifies that all URNs for works and versions
    * agree with their parent element.  Adds a one-line text
    * description of each error found to errorList.
    * @param root Parsed root element of the inventory.
    */
    void checkUrnHierarchy(groovy.util.Node root) {
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
    }

    /** Verifies that all textgroups belong to a CTS
    * namespace declared by a ctsnamespace element in the
    * XML representation of a TextInventory.  Adds a one-line
    * description of each error found to errorList.
    * @param root Parsed root element of the inventory.
    */
    void checkCtsNsDecl(groovy.util.Node root) {
        def declaredAbbrs = []
        root[ti.ctsnamespace].each {
            declaredAbbrs.add(it.'@abbr')
        }

        root[ti.textgroup].each { tg ->
            CtsUrn urn = new CtsUrn(tg.'@urn')
            if (! declaredAbbrs.containsAll(urn.getCtsNamespace())) {
                this.errorList.add("Undeclared cts namespace in urn ${urn}")
            }
        }

    }


    /** Verifies that values of xml:lang for translation 
    * elements is not identical to the xml:lang value of the
    * parent work. 
    */
    void checkLangAttrs(groovy.util.Node root) {
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
    }


    void checkXPath(groovy.util.Node citeNode, java.util.ArrayList validList, String urn) {

//        this.errorList.add("CHECK usage of ${citeNode.'@scope'} against ${validList} ")

        citeNode.'@scope'.split('/').each { chunk ->
            def nsParts = chunk.split(/:/)
            if (nsParts.size() > 1) {
                if (! validList.containsAll(nsParts[0])) {
                    this.errorList.add("For ${urn}, @scope attribute ${citeNode.'@scope'} uses undeclared XML namespace.")
                }
            }
        }


        citeNode.'@xpath'.split('/').each { chunk ->
            def nsParts = chunk.split(/:/)
            if (nsParts.size() > 1) {
                if (! validList.containsAll(nsParts[0])) {
                    this.errorList.add("For ${urn}, @xpath attribute ${citeNode.'@xpath'} uses undeclared XML namespace.")
                }
            }
        }

        citeNode[ti.citation].each { cn ->
            checkXPath(cn, validList, urn)
        }

    }



    void checkXmlNsUsage(groovy.util.Node root) {

        root[ti.textgroup][ti.work][ti.edition].each { e ->
            String urn = e.'@urn'
            e[ti.online].each { olNode ->
                def definedList = []        
                olNode[ti.namespaceMapping].each {
                    definedList.add(it.'@abbreviation')
                }
                olNode[ti.citationMapping][ti.citation].each { c ->
                    checkXPath(c, definedList, urn)
                }
            }
        }
    }

    /* data constraints to check:
    * - √ urns must inherit hierarchy
    * - √ memberof must be of a collection
    * - √ cts ns must be delcared
    * - √ lang attributes should be 3-char abbrs and xlation should != original
    * - √ recursively check @scope and @xpath values on all citationMappings: parse
    * for abbrs and check that they are declared.
    */
    void checkDataValues(groovy.util.Node root) 
    throws Exception {
        checkUrnHierarchy(root)
        checkCtsNsDecl(root)
        checkLangAttrs(root)
        checkXmlNsUsage(root)

        if (this.errorList.size() > 0) {
            throw new Exception(this.errorListToText())
        }
    }



    /** Constructs a TextInventory from a CTS GetCapabilities request.
    * @param capsUrl URL of the GetCapabilities request.
    * @throws Exception if invalid data values found.
    */
    TextInventory (URL capsUrl) 
    throws Exception {
        def capsText = capsUrl.getText("UTF-8")
        groovy.util.Node capsRoot = new XmlParser().parseText(capsText)

        def repl = capsRoot[ctsns.reply][0]
        def tiRoot = repl[ti.TextInventory][0]

        try {
            this.initFromParsed(tiRoot)
        } catch (Exception e) {
            throw e
        }
    }


    /** Constructs a TextInventory from a string serialization
    * of an TextInventory as XML.
    * @param str The String of XML text representing the TextInventory.
    * @throws Exception if invalid data values found.
    */
    TextInventory (String str) 
    throws Exception {
        try {
            this.initFromText(str)
        } catch (Exception e) {
            throw e
        }
    }

    /** Constructs a TextInventory from a File object.
    * @param f File with XML validating against the XML schema of a TextInventory.
    * @throws Exception if invalid data values found.
    */
    TextInventory (File f) 
    throws Exception {
        try {
            this.initFromText(f.getText(enc))
        } catch (Exception e) {
            throw e
        }
    }


    /** Constructs a TextInventory from the root node
    * of a parsed XML inventory.
    * @param docRoot Node giving the root of the parsed XML.
    */
    TextInventory(groovy.util.Node docRoot) {
        try {
            this.initFromParsed(docRoot)
        } catch (Exception e) {
            System.err.println "There were errors initializing the inventory:"
            this.errorList.each {
                System.err.println "\t${it}"
            }
        }
    }

    /** Constructs an empty TextInventory object.
    */
    TextInventory() {
    }

    /** Finds all online versions of all texts
    * known to the inventory.
    * @returns A List of version-level URNs.
    */
    def allOnline() {
        def onlineList = []
        allDataOnline().each { vers ->
            onlineList.add(vers[0])
        }
        return onlineList
    }

    /** Determines whether a version is an edition or
    * translation.
    * @param urnStr URN, as a String, of the version to examine.
    * @returns A VersionType value.
    * @throws Exception if urnStr is not a valid CTS URN string.
    */
    VersionType typeForVersion(String urnStr) 
    throws Exception {
        try { 
            def u = new CtsUrn(urnStr)
            return typeForVersion(urnStr)
        } catch (Exception e) {
            throw e
        }
    }


    /** Determines whether a version is an edition or
    * translation.
    * @param urnStr URN, as a String, of the version to examine.
    * @returns A VersionType value.
    */
    VersionType typeForVersion(CtsUrn urn) {
        def edLabel = editionLabel(urn)
        def xlatLabel = translationLabel(urn)
        if (edLabel) {
            return VersionType.EDITION
        } else if (xlatLabel) {
            return VersionType.TRANSLATION
        } else {
            return null
        }
    }


    /** Finds a map of the XML namespace abbreviations
    * used in a specific version of a document to full
    * URIs for the XML namespace.
    * @param urnStr Version-level Cts Urn string for
    * the document in question.
    * @returns A map of XML namespace abbreviations to URI values.
    * @throws Exception if urnStr is not a valid Cts Urn value.
    */
    LinkedHashMap xmlNsForVersion(String urnStr) 
    throws Exception {
        try {
            CtsUrn urn = new CtsUrn(urnStr)
            return xmlNsForVersion(urn)
        } catch (Exception e) {
            throw e
        }
    }



    /** Finds a map of the XML namespace abbreviations
    * used in a specific version of a document to full
    * URIs for the XML namespace.
    * @param urn Version-level Cts Urn for the document in question.
    * @returns A map of XML namespace abbreviations to URI values.
    */
    LinkedHashMap xmlNsForVersion(CtsUrn urn) {
        return nsMapList[urn.toString()]
    }



    /** Determines if a URN represents a text in the inventory.
    * @param urn The URN to test.
    * @returns true if the URN is in the inventory.
    */
    boolean urnInInventory(CtsUrn urn) {
        def nsStruct = this.ctsnamespaces.find { it[0] == urn.getCtsNamespace()}

        switch (urn.getWorkLevel()) {
            case CtsUrn.WorkLevel.VERSION:
                switch (typeForVersion(urn)) {
                case VersionType.EDITION:
                    String ed = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}.${urn.getVersion()}"
                def edStruct = this.editions.find {it[0] == ed}
                String tg = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}"
                def tgStruct = this.textgroups.find {it[0] == tg}
                String wk = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}"
                def wkStruct = this.works.find {it[0] == wk }
                return ((nsStruct != null) && (tgStruct != null) && (wkStruct != null))
                break

                case VersionType.TRANSLATION:
                    String trans = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}.${urn.getVersion()}"
                def transStruct = this.translations.find {it[0] == trans}
                String tg = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}"
                def tgStruct = this.textgroups.find {it[0] == tg}
                String wk = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}"
                def wkStruct = this.works.find {it[0] == wk }
                return ((nsStruct != null) && (tgStruct != null) && (wkStruct != null))

                break

                default:
                    System.err.println "TYpe is " + typeForVersion(urn)
                break
            } 

            case CtsUrn.WorkLevel.WORK:
                String tg = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}"
            def tgStruct = this.textgroups.find {it[0] == tg}
            String grp = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}"
            def wkStruct = this.works.find {it[0] == grp }
            return ((nsStruct != null) && (tgStruct != null) && (wkStruct != null))
            break

            case CtsUrn.WorkLevel.GROUP:
            String tg = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}"
            def tgStruct = this.textgroups.find {it[0] == tg}
            return ((nsStruct != null) && (tgStruct != null))
            break

            default: 
                System.err.println "Level " + urn.getWorkLevel()
            return true
            break
        }
        return false
    }



    /** Finds ISO language code for a specified version of a work.
    * @param urnStr URN value of the work in question.
    * @returns ISO language code.
    * @throws Exception if urnStr is not a valid CTS URN value.
    */
    String languageForVersion(String urnStr) 
    throws Exception {
        try {
            CtsUrn urn = new CtsUrn(urnStr)
            return languageForVersion(urn)

        } catch (Exception e) {
            throw e
        }
    }



    /** Finds ISO language code for a specified version of a work.
    * @param urnStr URN value of the work in question.
    * @returns ISO language code.
    */
    String languageForVersion(CtsUrn urn) {
        if (debug > 0)  {
            System.err.println "${urn} at work level " + urn.getWorkLevel()
        }

        switch (urn.getWorkLevel()) {
            case CtsUrn.WorkLevel.WORK : 
                return languageForWork(urn)
            break

            case CtsUrn.WorkLevel.VERSION : 

                if (debug > 0)  {
                System.err.println "${urn} at version level  is type" + typeForVersion(urn)
            }

               switch (typeForVersion(urn)) {
                
                case VersionType.TRANSLATION:
                    return translationLanguages[urn.toString()]
                break

                case VersionType.EDITION:
                    return languageForWork(urn)
                break

                default:
                    // 
                    break
            }
            break

            default :
                //
                break

        }
    }
    




    /** Finds the language code for a notional work.
    * @param urnStr A CtsUrn string identifying the work.
    * @returns A 3-letter language code, or null if no
    * work was found for the requested urn.
    * @throws Exception if urnStr is not a valid CTS URN string.
    */
    String languageForWork(String urnStr) 
    throws Exception {
        try {
            CtsUrn urn = new CtsUrn(urnStr)
            String workStr = "urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}"
            return worksLanguages[workStr]

        } catch (Exception e) {
            throw e
        }
    }

    /** Finds the language code for a notional work.
    * @param urn CtsUrn identifying the work.
    * @returns A 3-letter language code, or null if no
    * work was found for the requested urn.
    */
    String languageForWork(CtsUrn urn) {
        return worksLanguages["urn:cts:${urn.getCtsNamespace()}:${urn.getTextGroup()}.${urn.getWork()}"]
    }

    /** Finds value of online element's docname attribute.
    * @param urnStr The URN, as a String, of the document to look up.
    * @returns The docname attribute of the online element, or null
    * if none found.
    * @throws Exception if urnStr is not a valid CtsUrn.
    */
    String onlineDocname(String urnStr) {
        CtsUrn urn = new CtsUrn(urnStr)
        return onlineDocname(urn)
    }

    /** Finds value of online element's docname attribute.
    * @param urn The URN of the document to look up.
    * @returns The docname attribute of the online element, or null
    * if none found.
    * @throws Exception if urnStr is not a valid CtsUrn.
    */
    String onlineDocname(CtsUrn urn) {
        return onlineMap[urn.toString()]
    }

    /** Finds work-level URNs for all works
     * available online for the textgroup of a given CTS URN.
    * @param u A CTS URN to check for.
    * @returns A (possibly empty) List of work-level data structures.
    */
    def worksForGroup(CtsUrn u) {
        def workUrns =  []
        worksDataForGroup("urn:cts:${u.getCtsNamespace()}:${u.getTextGroup()}").each {
            workUrns.add(it[0])
        }
        return workUrns
    }


    /** Finds work-level URNs for all works
     * available online for the textgroup of a given CTS URN.
    * @param u A CTS URN to check for.
    * @returns A (possibly empty) List of work-level data structures.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def worksForGroup(String s) {
        def u 
        try {
            u = new CtsUrn(s)
            return worksForGroup(u)
        } catch (Exception e) {
            throw e
        }
    }

    /** Gets the full URI of the CTS Namespace identified by 
    * @param label Unique label of the CTS Namespace.
    * @returns The URI for the CTS Namespace, or null if none
    * found.
    * @throws Exception if more than one CTS Namespace has the
    * requested label.
    */
    String getNamespaceUri(String label) {
        def ctsNsMatches = this.ctsnamespaces.findAll {it[0] == label}
        switch (ctsNsMatches.size()) {
            case 0:
                return null
            break
            
            case 1:
                def ctsNs = ctsNsMatches[0]
                return ctsNs[1]
                break

                default :
                    throw new Exception("CTS Namespaces misconfigured: label ${label}is not unique.")
                break
            }
     }


    /**  Finds version-level URNs for all online versions
    * belonging to the TextGroup of the requested CtsUrn.
    * @param u CtsUrn identifying the TextGroup to search for.
    * @returns A (possibly empty) List of CtsUrn strings.
    */
    def onlineForGroup(CtsUrn u) {
        def versionList = []
        onlineDataForGroup(u).each { vers ->
            versionList.add(vers[0])
        }
        return versionList
    }

    /**  Finds version-level URNs for all online versions
    * belonging to the TextGroup of the requested CtsUrn.
    * @param u CtsUrn identifying the TextGroup to search for.
    * @returns A (possibly empty) List of CtsUrn strings.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def onlineForGroup(String s) {
        def u 
        try {
            u = new CtsUrn(s)
            return onlineForGroup(new CtsUrn(s))
        } catch (Exception e) {
            throw e
        }
    }


    /** Finds version-level URNs for all versions
     * available online for a given CTS URN.
    * @param u A CTS URN to check for.
    * This URN may include either a work- or version-level reference to the work:     
    * it is trimmed to the work level before searching.  If u is given at the 
    * text group level, it is not an error, but the returned list will be empty.
    * @returns A possibly empty List of Strings giving CtsUrns at the version 
    * level.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def onlineForWork(String s) {
        def u
        try {
            u = new CtsUrn(s)
            return onlineForWork(u)
        } catch (Exception e) {
            throw e
        }
    }

    /** Finds version-level URNs for all versions
     * available online for a given CTS URN.
    * @param u A CTS URN to check for.
    * This URN may include either a work- or version-level reference to the work:     * it is trimmed to the work level before searching.  If u is given at the 
    * text group level, it is not an error, but the returned list will be empty.
    * @returns A possibly empty List of Strings giving CtsUrns at the version 
    * level.
    */
    def onlineForWork(CtsUrn u) {
        def urns = []
        onlineDataForWork(u).each {
            urns.add(it[0])
        }
        return urns
    }

    /** Finds version-level URNs known to this inventory for a
    * requested CTS URN. 
    * @param u The CTS URN to search for.  This URN may include either a work-
    * or version-level reference to the work: it is trimmed to the work level
    * before searching.  If u is given at the text group level, it is not an
    * error, but the returned list will be empty.
    * @returns A possibly empty List of Strings giving CtsUrns at the version 
    * level.
    **/
    def versionsForWork(CtsUrn u) {
        def wk = "urn:cts:${u.getCtsNamespace()}:${u.getTextGroup(false)}.${u.getWork(false)}"
        def edlist = editions.findAll {
            it[3] == wk.toString()
        }

        def edUrls = []
        edlist.each { ed ->
            edUrls.add(ed[0])
        }


        def translist = translations.findAll {
            it[3] == wk.toString()
        }

        def transUrls = []
        translist.each { tr ->
            transUrls.add(tr[0])
        }

        return edUrls + transUrls
    }



    /** Finds version-level URNs known to this inventory for a
    * requested CTS URN. 
    * @param s A String representing the CTS URN to search for.  
    * This URN may include either a work- or version-level reference to the work:     * it is trimmed to the work level before searching.  If u is given at the 
    * text group level, it is not an error, but the returned list will be empty.
    * @returns A possibly empty List of Strings giving CtsUrns at the version 
    * level.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def versionsForWork(String s) {
        def urn
        try { 
            urn = new CtsUrn(s)
            versionsForWork(new CtsUrn(s))
        } catch (Exception e) {
            throw e
        }
    }


    /** Creates reabable name for text group identified by URN.
    * @param u URN identifying text group to label.
    * @returns A String with a human-readable name for the text group,
    * or null if no text group is found for the requested URN.
    * @throws Exception if urnStr is not a valid CTS URN string.
    */
    def getGroupName(String urnStr) 
    throws Exception {
        CtsUrn urn
        try {
            urn = new CtsUrn(urnStr)
        } catch (Exception e) {
            throw e
        }
        return getGroupName(urn)
    }


    /** Creates reabable name for text group identified by URN.
    * @param u URN identifying text group to label.
    * @returns A String with a human-readable name for the text group,
    * or null if no text group is found for the requested URN.
    */
    String getGroupName (CtsUrn u) {
        def tgStr = "urn:cts:${u.getCtsNamespace()}:${u.getTextGroup(false)}"
        def gp = this.textgroups.find {
            it[0] == tgStr
        }
        if (gp) {
            return gp[1]
        } else {
            return null
        }
    }


    /** Creates reabable name for text group identified by URN.
    * @param u URN identifying text group to label.
    * @returns A String with a human-readlabe name for the text group,
    * or null if no text group is found for the requested URN.
    * @throws Exception if s is not a valid CtsUrn String
    */

    String groupName(String s) {
        def u
        try {
            u =  new CtsUrn(s)
            return groupName(u)
        } catch (Exception e) {
            throw e
        }
    }



    /** Gets a reabable name for a work identified by URN.
    * @param u CTS URN identifying the work to label.
    * @returns A String with a human-readlabe name for the work,
    * or null if no work is found for the requested URN.
    */
    def workTitle (CtsUrn u) {
        def wk = this.works.find {
            (it[0] == u.toString())
        }
        if (wk) {
            wk[1]
        } else {
            null
        }
    }


    /** Creates reabable name for a work identified by URN.
    * @param u URN identifying the work to label.
    * @returns A String with a human-readlabe name for the work,
    * or null if no work is found for the requested URN.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def workTitle(String s) {
        def u
        try {
            u = new CtsUrn(s)
            return workTitle(u)
        } catch (Exception e) {
            throw e
        }
    }


    /** Creates reabable name for a translation identified by URN.
    * @param u URN identifying the translation to label.
    * @returns A String with a human-readable name for the translation,
    * or null if no translation is found for the requested URN.
    */
    String translationLabel (CtsUrn u) {
        def xlat = translations.find {
            it[0] == u.toString()
        }
        if (xlat) {
            return xlat[1]
        } else { return null }
    }

    /** Creates reabable name for a translation identified by URN.
    * @param u URN identifying the translation to label.
    * @returns A String with a human-readable name for the translation,
    * or null if no translation is found for the requested URN.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def translationLabel (String s) {
        def u
        try {
            u = new CtsUrn(s)
            return translationLabel(u)
        } catch (Exception e) {
            throw e
        }
    }



    /** Creates reabable name for an edition identified by URN.
    * @param u URN identifying the edition to label.
    * @returns A String with a human-readable name for the edition,
    * or null if no edition is found for the requested URN.
    */
    String editionLabel (CtsUrn u) {
        def ed = editions.find {
            it[0] == u.toString() 
        }
        if (ed) {
            return ed[1]
        } else { return null }
    }

    /** Creates reabable name for an edition identified by URN.
    * @param u URN identifying the edition to label.
    * @returns A String with a human-readable name for the edition,
    * or null if no edition is found for the requested URN.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def editionLabel(String s) {
        def u
        try {
            u = new CtsUrn(s)
            return editionLabel(u)
        } catch (Exception e) {
            throw e
        }
    }


   /** Creates reabable name for an edition identified by URN.
    * @param u URN identifying the edition to label.
    * @returns A String with a human-readable name for the version,
    * (either translation or edition) or null if no version is found for the requested URN.
    */
    String versionLabel(CtsUrn u) {
        def edLabel = editionLabel(u)
        def xlatLabel = translationLabel(u)
        if (edLabel) {
            return edLabel
        } else if (xlatLabel) {
            return xlatLabel
        } else {
            return null
        }
    }


   /** Creates reabable name for an edition identified by URN.
    * @param u URN identifying the edition to label.
    * @returns A String with a human-readable name for the version,
    * (either translation or edition) or null if no version is found for the requested URN.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def versionLabel(String s) {
        def u
        try {
            u = new CtsUrn(s)
            return versionLabel(u)
        } catch (Exception e) {
            throw e
        }
    }


    /* ********************************************************************/
    /* ** Begin "private" or internal methods *****************************/

    /**
    * Finds a CitationModel object for a work identified by URN, as a String.
    * @param The URN, as a String, of the work for which to find
    * the CitationModel.
    * @returns A CitationModel derived from the online node corresponding
    * to this URN, or null if no match found.
    */
    CitationModel getCitationModel(String urnStr)  {
      if (debug > 1) {
	System.err.println "CM map keys are: " + this.citationModelMap.keySet()
	System.err.println "Includes ${urnStr}? " + this.citationModelMap.keySet().contains(urnStr)
	System.err.println "So we return a " + this.citationModelMap[urnStr].getClass()
	def modelMap = this.citationModelMap[urnStr]
	System.err.println "Number of its mappings = " + modelMap.mappings.size()
      }
      return this.citationModelMap[urnStr]
    }


    /**
    * Finds a CitationModel object for a work identified by URN.
    * @param The URN the work for which to find the CitationModel.
    * @returns A CitationModel derived from the online node corresponding
    * to this URN, or null if no match found.
    */
    CitationModel getCitationModel(CtsUrn urn)  {
        return getCitationModel(urn.toString())
    }




    /** "Private" method populates TI model from a string serialization of a  
    * CTS TextInventory.
    * @param str String giving content of a valid XML inventory.
    */
    void initFromText (String str) {
        def root = new XmlParser().parseText(str)
        initFromParsed(root, true)
    }


    void initFromParsed (groovy.util.Node root) {
        initFromParsed(root, true)
    }

    /** "Private" method populates TI model from an XML representation
    * of a CTS TextInventory that has been parsed as a
    * groovy Node object.
    * @root The root Node of the parse.
    * @throws Exception if data values violate definition of TextInventory.
    */
    void initFromParsed (groovy.util.Node root, boolean checkData) 
    throws Exception {
        if (checkData) {
            try {
                checkDataValues(root)
            } catch(Exception e) {
                throw e
            }
        }
        
        //this.tiId = root.'@tiid'
        this.tiVersion = root.'@tiversion'
        root[ti.ctsnamespace].each { ctsns ->
            def abbr = ctsns.'@abbr'
            def uri = ctsns.'@ns'
            def descr = ctsns[ti.description][0]?.text()
            def nsTriple = [abbr, uri, descr]
            ctsnamespaces.add(nsTriple)
        }

        root[ti.textgroup].each { g ->
            def nameNode = g[ti.groupname][0]
            textgroups.add([g.'@urn',nameNode?.text()])
	    if (debug > 1) {
	      System.err.println "Found text group " + nameNode?.text()
	    }

            g[ti.work].each { w ->
                def titleNode = w[ti.title][0]
                works.add([w.'@urn',titleNode?.text(), g.'@urn'])
		if (debug > 1) {
		  System.err.println "Found  work " + titleNode?.text()
		}

                //works.add(w.'@urn')
                w.attributes().each { a ->
                    def k = a.getKey()
                    if (k instanceof groovy.xml.QName) {
                        if (k.getLocalPart() == "lang") {
                            worksLanguages[w.'@urn'] = a.getValue()
                        }
                    }
                }


                w[ti.edition].each { e ->
                    def labelNode = e[ti.label][0]

                    def online = e[ti.online]
                    boolean isOnline = (online.size() > 0)

		    if (debug > 1) {
		      System.err.println "Found  edition " + labelNode?.text()
		      System.err.println "Online? " + isOnline
		    }
		    
                    editions.add([e.'@urn',labelNode?.text(),isOnline, w.'@urn'])
                    if (isOnline)  {
                        def firstOnline = online[0]
                        onlineMap[e.'@urn'] = firstOnline.'@docname'

                        citationModelMap[e.'@urn'] = new CitationModel(firstOnline)
			if (debug > 1) {
			  System.err.println "Make CM for " + firstOnline
			  System.err.println " indexed from " + e.'@urn'
			  System.err.println "Size of model map now " + citationModelMap.keySet().size()
			  
			  
			}

                        def nsMaps = [:]
                        e[ti.online][ti.namespaceMapping].each { ns ->
                            String abbr = ns.'@abbreviation'
                            String uri = ns.'@nsURI'
                            nsMaps[abbr] = uri
                        }
                        nsMapList[e.'@urn'] = nsMaps
                    }
                }


                w[ti.translation].each { tr ->
                    tr.attributes().each { a ->
                        def k = a.getKey()
                        if (k instanceof groovy.xml.QName) {
                            if (k.getLocalPart() == "lang") {
                                translationLanguages[tr.'@urn'] = a.getValue()

                            }
                        }
                    }
                    def labelNode = tr[ti.label][0]

                    def online = tr[ti.online]
                    boolean isOnline = (online.size() > 0)
		    if (debug > 1) {
		      System.err.println "Found translation " + labelNode?.text()
		      System.err.println "Online? " + isOnline
		    }
                    translations.add([tr.'@urn',labelNode?.text(),isOnline, w.'@urn'])
                    if (isOnline)  {
                        def firstOnline = online[0]
                        onlineMap[tr.'@urn'] = firstOnline.'@docname'
                        citationModelMap[tr.'@urn'] = new CitationModel(firstOnline)

                        def nsMaps = [:]
                        tr[ti.online][ti.namespaceMapping].each { ns ->
                            String abbr = ns.'@abbreviation'
                            String uri = ns.'@nsURI'
                            nsMaps[abbr] = uri
                        }
                        nsMapList[tr.'@urn'] = nsMaps

                    }

                }
            }
        }

    }



    /** "Private" method finds data structures for all online versions of 
    * texts known to the inventory.
    * @returns A List of version-level data structures.
    */
    def allDataOnline() {
        def onlineEdd = editions.findAll {
            it[2] == true
        }
        def onlineTranss = translations.findAll {
            it[2] == true
        }
        return onlineEdd + onlineTranss
    }



    /** "Private" or internal method finds work-level data structures for all works
     * available online for the textgroup of a given CTS URN.
    * @param u A CTS URN to check for.
    * @returns A (possibly empty) List of work-level data structures.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def worksDataForGroup(String s) {
        def u 
        try {
            u = new CtsUrn(s)
            return worksDataForGroup(u)
        } catch (Exception e) {
            throw e
        }
    }

    /** "Private" or internal method finds work-level data structures for all works
     * available online for the textgroup of a given CTS URN.
    * @param u CTS URN of a textgroup to check for.
    * @returns A (possibly empty) List of work-level data structures.
    */
    def worksDataForGroup(CtsUrn u) {
        def wkList = works.findAll {
            (it[2] == u.toString())
        }
        return wkList
    }





    /**   "Private" or internal method finds version-level data structures for all online versions
    * belonging to the TextGroup of the requested CtsUrn.
    * @param u CtsUrn identifying the TextGroup to search for.
    * @returns A (possibly empty) List of version data structures.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def onlineDataForGroup(String s) {
        def u 
        try {
            u = new CtsUrn(s)
            return onlineDataForGroup(new CtsUrn(s))
        } catch (Exception e) {
            throw e
        }
    }

    /**   "Private" or internal method finds version-level data structures 
    * for all online versions belonging to the TextGroup of the requested 
    * CtsUrn.
    * @param u CtsUrn identifying the TextGroup to search for.
    * @returns A (possibly empty) List of version data structures.
    */
    def onlineDataForGroup(CtsUrn u) {
        def online = []
        def wks = worksDataForGroup(u)
        wks.each { w ->
            def workList = onlineDataForWork(w[0])
            online = online + workList
        }
        return online
    }




    /**  "Private" or internal method finds version-level data structures for all versions
     * available online for a given CTS URN.
    * @param u A CTS URN to check for.
    * @returns A (possibly empty) List of version-level data structures.
    */
    def onlineDataForWork(CtsUrn u) {
        def edlist = editions.findAll {
            ((it[3] == u.toString()) & (it[2] == true))
        }
        def edUrls = []
        edlist.each { ed ->
            edUrls.add(ed)
        }

        def trlist = translations.findAll {
            ((it[3] == u.toString()) & (it[2] == true))
        }

        def transUrls = []
        trlist.each { tr ->
            transUrls.add(tr)
        }
        return edUrls + transUrls
    }




    /**  "Private" or internal method finds version-level data structures for all versions
     * available online for a given CTS URN.
    * @param u A CTS URN to check for 
    * This URN may include either a work- or version-level reference to the work:     
    * it is trimmed to the work level before searching.  If u is given at the 
    * text group level, it is not an error, but the returned list will be empty.
    * @returns A (possibly empty) List of version-level data structures.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def onlineDataForWork(String s) {
        def u 
        try {
            u = new CtsUrn(s)
            return onlineDataForWork(new CtsUrn(s))
        } catch (Exception e) {
            throw e
        }
    }




    /**  "Private" or internal method finds version data structures for the work identified by
    * requested CTS URN. 
    * @param s A String representing the CTS URN to search for.  
    * This URN may include either a work- or version-level reference to the work:     
    * it is trimmed to the work level before searching.  If u is given at the 
    * text group level, it is not an error, but the returned list will be empty.
    * @returns A possibly empty List of Strings giving CtsUrns at the version 
    * level.
    * @throws Exception if s is not a valid CtsUrn String
    */
    def versionsDataForWork(String s) {
        def u
        try { 
            u = new CtsUrn(s)
            return versionsDataForWork(u)
        } catch (Exception e) {
            throw e
        }
    }

    /**  "Private" or internal method finds version data structures for the work identified by
    * requested CTS URN. 
    * @param s A String representing the CTS URN to search for.  
    * This URN may include either a work- or version-level reference to the work:     
    * it is trimmed to the work level before searching.  If u is given at the 
    * text group level, it is not an error, but the returned list will be empty.
    * @returns A possibly empty List of Strings giving CtsUrns at the version 
    * level.
    */
    def versionsDataForWork(CtsUrn u) {
        def wk = "${u.getCtsNamespace()}:${u.getTextGroup(false)}.${u.getWork(false)}"
        def edList = editions.findAll {
            it[0] == wk.toString()
        }
        def transList = translations.findAll {
            it[0] == wk.toString()
        }
        return edList + transList
    }

    /* ** End "private" or internal methods *******************************/
    /* ********************************************************************/



    /* ********************************************************************/
    /* ** INCOMPLETE/EXPERIMENTAL STUFF... *******************************/



    /**  Serializes model of TextInventory as an XML string
    * validating against the TextInventory schema.
    * HIHGLY INCOMPLETE IMPLENTATION:  NOT FULLY VALID.
    * @returns A String of XML representing the TextInventory.
    */
    String asXml() {

        def xml = new groovy.xml.StreamingMarkupBuilder().bind {
            mkp.declareNamespace('':'http://chs.harvard.edu/xmlns/cts3/ti')
            mkp.declareNamespace('dc':'http://purl.org/dc/elements/1.1')
            
            TextInventory(tiversion : "${this.tiVersion}") { 
	      //                          tiid : "${this.tiId}"  ) {


                // One or more ollections with DC metadata go here, one of which is
                // marked with isdefault="yes" ...
                collection(id:"COLLECTIONID",isdefault:"yes") {
                    title('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1", "TITLE")
                    creator( xmlns:"http://purl.org/dc/elements/1.1","CREATOR")
                    coverage('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1","COVERAGE")
                    description('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1","DESCRIPTION")
                    rights('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1","RIGHTS")
                }





/*                this.ctsnamespaces.each { c ->
                    def desc = c[2]
                    ctsnamespace(abbr : "${c[0]}",
                                 ns : "${c[1]}") {
                        description('xml:lang': "ADDLANG", "${desc}")
                    }
                } */

                this.textgroups.each { tg ->
                    def tgUrn = new CtsUrn("${tg[0]}")
                    textgroup(urn : "${tgUrn.getTextGroup()}" ) {
                        groupname('xml:lang': "ADDLANG","${tg[1]}")
                        def wkList = worksForGroup(tgUrn)
                        wkList.each { w ->
                            CtsUrn workUrn = new CtsUrn(w)
                            def versionsOnline = onlineDataForWork(workUrn)
                            if (versionsOnline.size() > 0) {
                                work('xml:lang': "ADDLANG", urn : "${workUrn.getWork(true)}") {
                                    title('xml:lang': "ADDLANG","${this.workTitle(workUrn)}")
                                    versionsOnline.each { v ->
                                        def versionUrn = new CtsUrn(workUrn.toString() + "." + v[1] )

                                        switch (typeForVersion(versionUrn)) {
                                            case VersionType.EDITION:

                                                edition(urn : "${tgUrn.getCtsNamespace()}:${v[1]}") {
                                                label ('xml:lang': "ADDLANG","${v[2]}")
                                                description ('xml:lang': "ADDLANG","${v[2]}")
                                                def onlineDoc = this.onlineDocname(versionUrn)

                                                online(docname : onlineDoc) {
                                                    validate(schema: "VALIDATE SCHEMA")
                                                    namespaceMapping(nsURI:"uri",abbreviation: "NAMESPACEABBR")
                                                    def cm = this.getCitationModel(versionUrn)
                                                    citationMapping {
                                                    cm.mappings.each { m ->
                                                        assert m instanceof java.util.ArrayList 


                                                        def maxIndex = m.size() - 1
                                                        def depth = 0
                                                        def triplet = m[depth]
                                                        citation (label : "${triplet.getLabel()}", 
                                                                         scope : "${triplet.getScopePattern()}", 
                                                                         xpath:  "${triplet.getLeafPattern()}" ) {

                                                            
                                                            // don't know how to get Builder to handle recursion. 
                                                            // manually allow up to 4 levels.  sigh. 
                                                            if (maxIndex > depth) {
                                                                depth++;
                                                                triplet = m[depth]
                                                            citation (label : "${triplet.getLabel()}", 
                                                                             scope : "${triplet.getScopePattern()}", 
                                                                             xpath:  "${triplet.getLeafPattern()}" ) {


                                                                if (maxIndex > depth) {
                                                                    depth++;
                                                                    triplet = m[depth]
                                                                citation (label : "${triplet.getLabel()}", 
                                                                                 scope : "${triplet.getScopePattern()}", 
                                                                                 xpath:  "${triplet.getLeafPattern()}" ) {
                                                                    
                                                                    if (maxIndex > depth) {
                                                                        depth++;
                                                                        triplet = m[depth]
                                                                    citation (label : "${triplet.getLabel()}", 
                                                                                     scope : "${triplet.getScopePattern()}", 
                                                                                     xpath:  "${triplet.getLeafPattern()}" ) {

                                                                        
                                                                        if (maxIndex > depth) {
                                                                            depth++;
                                                                            triplet = m[depth]
                                                                            citation (label : "${triplet.getLabel()}", 
                                                                                             scope : "${triplet.getScopePattern()}", 
                                                                                             xpath:  "${triplet.getLeafPattern()}" ) {
                                                                            }
}
                                                                        }
                                                                    }                                                                        
                                                                }
                                                           }
                                                            
}
                                                                }
                                                            }
                                                        }
                                                    }
                                                 
                                            }
}
                                            break

                                            case VersionType.TRANSLATION:

 translation(urn : "${tgUrn.getCtsNamespace()}:${v[1]}", 'xml:lang': "TRANSLATIONLANG HERE") {
                                                label ("${v[2]}", 'xml:lang': "XMLLANGABBR")
                                                description ('xml:lang': "ADDLANG","${v[2]}")
                                                def onlineDoc = this.onlineDocname(versionUrn)

                                                
                                                online(docname : onlineDoc) {
                                                    validate(schema: "VALIDATE SCHEMA")
                                                    namespaceMapping(nsURI:"uri",abbreviation: "NAMESPACEABBR")
                                                    def cm = this.getCitationModel(versionUrn)
                                                    citationMapping {
                                                    cm.mappings.each { m ->
                                                        assert m instanceof java.util.ArrayList 

                                                        def maxIndex = m.size() - 1
                                                        def depth = 0
                                                        def triplet = m[depth]
                                                        citation (label : "${triplet.getLabel()}", 
                                                                         scope : "${triplet.getScopePattern()}", 
                                                                         xpath:  "${triplet.getLeafPattern()}" ) {

                                                            
                                                            // don't know how to get Builder to handle recursion. 
                                                            // manually allow up to 4 levels.  sigh. 
                                                            if (maxIndex > depth) {
                                                                depth++;
                                                                triplet = m[depth]
                                                            citation (label : "${triplet.getLabel()}", 
                                                                             scope : "${triplet.getScopePattern()}", 
                                                                             xpath:  "${triplet.getLeafPattern()}" ) {


                                                                if (maxIndex > depth) {
                                                                    depth++;
                                                                    triplet = m[depth]
                                                                citation (label : "${triplet.getLabel()}", 
                                                                                 scope : "${triplet.getScopePattern()}", 
                                                                                 xpath:  "${triplet.getLeafPattern()}" ) {
                                                                    
                                                                    if (maxIndex > depth) {
                                                                        depth++;
                                                                        triplet = m[depth]
                                                                    citation (label : "${triplet.getLabel()}", 
                                                                                     scope : "${triplet.getScopePattern()}", 
                                                                                     xpath:  "${triplet.getLeafPattern()}" ) {

                                                                        
                                                                        if (maxIndex > depth) {
                                                                            depth++;
                                                                            triplet = m[depth]
                                                                            citation (label : "${triplet.getLabel()}", 
                                                                                             scope : "${triplet.getScopePattern()}", 
                                                                                             xpath:  "${triplet.getLeafPattern()}" ) {
                                                                            }
}
                                                                        }
                                                                    }                                                                        
                                                                }
                                                           }
                                                            
}
                                                                }
                                                            }
                                                        }
                                                    }
                                                 
                                            }
}
                                            break

                                            default:
                                                err("Very very bad.")
                                            break


                                        }
                                    }
                                }
                            }
                            
                        }
                        
                    }
                }
            }
        }

        xml.toString()
    }



}
