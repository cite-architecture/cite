package edu.harvard.chs.cite

import groovy.xml.StreamingMarkupBuilder


/**
*/
class TextInventorySerializer {

  TextInventory inv

  TextInventorySerializer(TextInventory ti) {
    this.inv = ti
  }
  
    /**  Serializes model of TextInventory as an XML string
    * validating against the TextInventory schema.
    * HIHGLY INCOMPLETE IMPLENTATION:  NOT FULLY VALID.
    * @returns A String of XML representing the TextInventory.
    */
    String toXml() {

        def xml = new groovy.xml.StreamingMarkupBuilder().bind {
            mkp.declareNamespace('':'http://chs.harvard.edu/xmlns/cts3/ti')
            mkp.declareNamespace('dc':'http://purl.org/dc/elements/1.1')

            TextInventory(tiversion : "${this.invtiVersion}") {
	      //                          tiid : "${this.invtiId}"  ) {


                // One or more ollections with DC metadata go here, one of which is
                // marked with isdefault="yes" ...
                collection(id:"COLLECTIONID",isdefault:"yes") {
                    title('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1", "TITLE")
                    creator( xmlns:"http://purl.org/dc/elements/1.1","CREATOR")
                    coverage('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1","COVERAGE")
                    description('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1","DESCRIPTION")
                    rights('xml:lang':"eng", xmlns:"http://purl.org/dc/elements/1.1","RIGHTS")
                }





/*                this.invctsnamespaces.each { c ->
                    def desc = c[2]
                    ctsnamespace(abbr : "${c[0]}",
                                 ns : "${c[1]}") {
                        description('xml:lang': "ADDLANG", "${desc}")
                    }
                } */

                this.invtextgroups.each { tg ->
                    def tgUrn = new CtsUrn("${tg[0]}")
                    textgroup(urn : "${tgUrn.getTextGroup()}" ) {
                        groupname('xml:lang': "ADDLANG","${tg[1]}")
                        def wkList = worksForGroup(tgUrn)
                        wkList.each { w ->
                            CtsUrn workUrn = new CtsUrn(w)
                            def versionsOnline = onlineDataForWork(workUrn)
                            if (versionsOnline.size() > 0) {
                                work('xml:lang': "ADDLANG", urn : "${workUrn.getWork(true)}") {
                                    title('xml:lang': "ADDLANG","${this.invworkTitle(workUrn)}")
                                    versionsOnline.each { v ->
                                        def versionUrn = new CtsUrn(workUrn.toString() + "." + v[1] )

                                        switch (typeForVersion(versionUrn)) {
                                            case VersionType.EDITION:

                                                edition(urn : "${tgUrn.getCtsNamespace()}:${v[1]}") {
                                                label ('xml:lang': "ADDLANG","${v[2]}")
                                                description ('xml:lang': "ADDLANG","${v[2]}")
                                                def onlineDoc = this.invonlineDocname(versionUrn)

                                                online(docname : onlineDoc) {
                                                    validate(schema: "VALIDATE SCHEMA")
                                                    namespaceMapping(nsURI:"uri",abbreviation: "NAMESPACEABBR")
                                                    def cm = this.invgetCitationModel(versionUrn)
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
                                                def onlineDoc = this.invonlineDocname(versionUrn)


                                                online(docname : onlineDoc) {
                                                    validate(schema: "VALIDATE SCHEMA")
                                                    namespaceMapping(nsURI:"uri",abbreviation: "NAMESPACEABBR")
                                                    def cm = this.invgetCitationModel(versionUrn)
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
