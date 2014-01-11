package edu.harvard.chs.cite

import groovy.xml.StreamingMarkupBuilder


/**
*/
class CollectionInventory {

    /** List of data values that violate the CollectionInventory definition. */
    def errorList = []

    /** Character encoding to use when representing CollectionInventory as 
    * a File object.
    */
    String enc = "UTF-8"

    /** XML namespace for the CITE Collection vocabulary.    */
    static groovy.xml.Namespace cite = new groovy.xml.Namespace("http://chs.harvard.edu/xmlns/cite")


    def extensionImpls = [:]

    def citeCollections = [:]


    /** Constructs a CollectionInventory from a CTS GetCapabilities request.
    * @param capsUrl URL of the GetCapabilities request.
    * @throws Exception if invalid data values found.
    */
    CollectionInventory (URL capsUrl) 
    throws Exception {
        def capsText = capsUrl.getText("UTF-8")
        groovy.util.Node capsRoot = new XmlParser().parseText(capsText)

        def repl = capsRoot[ctsns.reply][0]
        def tiRoot = repl[ti.CollectionInventory][0]

        try {
            this.initFromParsed(tiRoot)
        } catch (Exception e) {
            throw e
        }
    }


    /** Constructs a CollectionInventory from a string serialization
    * of an CollectionInventory as XML.
    * @param str The String of XML text representing the CollectionInventory.
    * @throws Exception if invalid data values found.
    */
    CollectionInventory (String str) 
    throws Exception {
        try {
            this.initFromText(str)
        } catch (Exception e) {
            throw e
        }
    }

    /** Constructs a CollectionInventory from a File object.
    * @param f File with XML validating against the XML schema of a CollectionInventory.
    * @throws Exception if invalid data values found.
    */
    CollectionInventory (File f) 
throws Exception {
        try {
            this.initFromText(f.getText(enc))
        } catch (Exception e) {
            throw e
        }
    }


    /** Constructs a CollectionInventory from the root node
    * of a parsed XML inventory.
    * @param docRoot Node giving the root of the parsed XML.
    */
    CollectionInventory(groovy.util.Node docRoot) {
        try {
            this.initFromParsed(docRoot)
        } catch (Exception e) {
            System.err.println "There were errors initializing the inventory:"
            this.errorList.each {
                System.err.println "\t${it}"
            }
        }
    }

    /** Constructs an empty CollectionInventory object.
    */
    CollectionInventory() {
    }

    // null if no redirect found
    String getRedirect(String urnStr, String requestName) 
    throws Exception {
        try {
            CiteUrn u = new CiteUrn(urnStr)
            return getRedirect(u, requestName)
        } catch (Exception e) {
        throw e
        }        
    }


    String getRedirect(CiteUrn urn, String requestName) {
        def conf = this.getCollectionConfig(urn)
        def extensions = conf['extensions']
        if (extensions[requestName]) {
            String svc = extensions[requestName]
            return "${this.extensionImpls[svc]}request=${requestName}&urn=${urn}"
        } else {
            return null
        }
    }

    String getCanonicalIdProperty(String urnStr) 
    throws Exception {
        try {
            CiteUrn u = new CiteUrn(urnStr)
            return getCanonicalIdProperty(u)
        } catch (Exception e) {
        throw e
        }
    }

    // or null if none found...
    String getCanonicalIdProperty(CiteUrn urn) {
        String keyString = "urn:cite:${urn.getNs()}:${urn.getCollection()}"
        def config =  this.citeCollections[keyString]
        return config['canonicalId']
    }



    def getCollectionConfig(String urnStr) 
    throws Exception {
        try {
            CiteUrn u = new CiteUrn(urnStr)
            return getCollectionConfig(u)
        } catch (Exception e) {
        }
    }

    def getCollectionConfig(CiteUrn urn) {
        String keyString = "urn:cite:${urn.getNs()}:${urn.getCollection()}"
        return this.citeCollections[keyString]
    }


    /** "Private" method populates TI model from a string serialization of a  
    * CTS CollectionInventory.
    * @param str String giving content of a valid XML inventory.
    */
    void initFromText (String str) {
        def root = new XmlParser().parseText(str)
        initFromParsed(root)
    }


    void initFromParsed (groovy.util.Node root) {
        initFromParsed(root, true)
    }

    /** "Private" method populates TI model from an XML representation
    * of a CTS CollectionInventory that has been parsed as a
    * groovy Node object.
    * @param root The root Node of the parse.
    * @param checkData
    * @throws Exception if data values violate definition of CollectionInventory.
    */
    void initFromParsed (groovy.util.Node root, boolean checkData) 
    throws Exception {

        // collect extensionImplementation data
        root[cite.extensionImplementation].each { ext ->
            this.extensionImpls[ext.'@name'] = ext.'@baseUrl'
        }

        // collect for all collections: name, urn, canonicalId
        // if extendedBy present, map its abbr from collect
        root[cite.citeCollection].each { coll ->
            def exts = [:]
            coll[cite.extendedBy].each { ext ->
                ext[cite.request].each { req ->
                    exts[req.'@name'] = ext.'@abbr'
                }
            }
            def dataPairs = [:]
            dataPairs['canonicalId'] = coll.'@canonicalId'
            dataPairs['extensions'] = exts
            
            this.citeCollections[coll.'@urn'] = dataPairs
        }
    }
}
