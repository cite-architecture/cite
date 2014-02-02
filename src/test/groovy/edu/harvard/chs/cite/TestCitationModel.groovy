package edu.harvard.chs.cite

import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CitationModel class
*/
class TestCitationModel extends GroovyTestCase {

    String pathToTestData = "unittests/data"
    TextInventory testTI = new TextInventory(new File("${pathToTestData}/unittests-inventory.xml" ))


    void testModelClasses() {
        def cmMap =  testTI.getCitationModelMap()
        cmMap.keySet().each { k ->
            def cm = cmMap[k]
            assert cm instanceof edu.harvard.chs.cite.CitationModel
            cm.mappings.each { m ->
                assert m instanceof java.util.ArrayList 
                CitationTriplet firstTriple = m[0]
                assert firstTriple instanceof edu.harvard.chs.cite.CitationTriplet
            }
        }
    }


    void testVersionRetrieval() {
        def octUrnStr = "urn:cts:greekLit:tlg0012.tlg001.oct"
        CtsUrn octUrn = new CtsUrn(octUrnStr)

        def versionUrnStr = "urn:cts:greekLit:tlg0013.tlg011.chs01"
        CtsUrn  versionUrn = new CtsUrn(versionUrnStr)

	CitationModel cm = testTI.getCitationModel(octUrn)
        assert cm

        // cm.mappings is a list of CitationTriplets, one for each
        // hierarchical system in a work.
        // There is only one hierarchy for the Iliad:
        def expectedHierarchies = 1
        assert cm.mappings.size() == expectedHierarchies

        // Iliad is cited by "book", "line"
        def hierarchy = cm.mappings[0]
        
        def topLevel = hierarchy[0]
        def secondLevel = hierarchy[1]

        assert topLevel.getLabel() == "book"
        assert secondLevel.getLabel() == "line"

    }

}


