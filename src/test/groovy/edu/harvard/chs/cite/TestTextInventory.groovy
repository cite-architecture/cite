package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


/** Class to test the cite library's TextInventory class. 
*/
class TestTextInventory extends GroovyTestCase {

    String testTextInvPath = "unittests/data/hmtTI.xml"
    File f = new File(testTextInvPath)
    TextInventory ti = new TextInventory(f)


    // values for testing
    String homerUrnStr = "urn:cts:greekLit:tlg0012"
    CtsUrn homerUrn = new CtsUrn(homerUrnStr)

    String iliadUrnStr = "urn:cts:greekLit:tlg0012.tlg001"
    CtsUrn iliadUrn = new CtsUrn(iliadUrnStr)

    String venetusAUrnStr = "urn:cts:greekLit:tlg0012.tlg001.msA"
    CtsUrn venetusAUrn = new CtsUrn(venetusAUrnStr)

    String proclusUrnStr = "urn:cts:greekLit:tlg4036"
    CtsUrn proclusUrn = new CtsUrn(proclusUrnStr)
    String chrestomathyUrnStr = "urn:cts:greekLit:tlg4036.tlg023"
    CtsUrn chrestomathyUrn = new CtsUrn(chrestomathyUrnStr)

    String metricalSummariesUrnStr = "urn:cts:greekLit:tlg5026.chs01"
    CtsUrn metricalSummariesUrn = new CtsUrn(metricalSummariesUrnStr)

    String metricalSummariesEnglishUrnStr = "urn:cts:greekLit:tlg5026.met.chs01"
    CtsUrn metricalSummariesEnglishUrn = new CtsUrn(metricalSummariesEnglishUrnStr)

    String bogusReference = "urn:cts:bogusNamespace:bogusGroup"
    String invalidUrnString = "not in URN syntax"



    /** Tests five signatures of the constructor */
    @Test void testConstructor() {
        // Constructor accepts four types of object for initialization
        // (Fourth type of constructor is URL:  see following tests)
        File f = new File(testTextInvPath)
        String s = f.getText("UTF-8")
        Node root = new XmlParser().parse(f)

        TextInventory ti1 = new TextInventory(f)
        TextInventory ti2 =  new TextInventory(s)
        TextInventory ti3 = new TextInventory(root)

        // These three should result in equivalent inventories:
        def invs = [ti1, ti2, ti3]

        // Expected values from test inventory:
        def expectedTgSize = 4
        def expectedWorksSize = 43
        def expectedEditionsSize = 202
        def expectedOnlineSize  = 11
        // test against all 3 inventories:
        invs.each { i ->
            assert i.textgroups.size() == expectedTgSize
            assert i.works.size() == expectedWorksSize
            assert i.editions.size() == expectedEditionsSize
        }

        // Fourth constrcutor signature accepts URL of a CTS GetCapabilities request.
        boolean onlineNow = false
        if (onlineNow) {
            def capsUrl = new URL("http://cts-demo.appspot.com/CTS?request=GetCapabilities")
            def onlineTi = new TextInventory(capsUrl)
            def expectedCapsEditionSize = 2
            assert onlineTi.editions.size() == expectedCapsEditionSize
            
            // Can also construct empty TI object:
            TextInventory emptyTI = new TextInventory()
            assert emptyTI.textgroups.size() == 0
            assert emptyTI.works.size() == 0
            assert emptyTI.editions.size() == 0
        }
    }


    @Test void testInvAttributes() {
      //assert ti.getTiId() == "hmt"
      assert ti.getTiVersion() == "5.0.rc.1"
    }

    @Test void testCtsNamespaces() {
        assert ti.ctsnamespaces.size() == 1
        assert ti.getNamespaceUri("greekLit") == "http://chs.harvard.edu/ctsns/greekLit"
    }


    @Test void testGroupNames() {
        def expectedLabel = "Homer"
        assert ti.getGroupName(homerUrnStr) == expectedLabel
        assert ti.getGroupName(homerUrn) == expectedLabel
    }


    @Test void testOnlineValues() {
        def allExpectedOnline =  [
            "urn:cts:greekLit:tlg0012.tlg001.msA", 
            "urn:cts:greekLit:tlg0012.tlg001.msB", 

            "urn:cts:greekLit:tlg1194.tlg002.chs01", 
            "urn:cts:greekLit:tlg4036.tlg023.chs01", 
            "urn:cts:greekLit:tlg4036.tlg023.chs02", 
            
            "urn:cts:greekLit:tlg5026.met.msA", 
            "urn:cts:greekLit:tlg5026.met.chs01",

            "urn:cts:greekLit:tlg5026.msAim.chs01", 
            "urn:cts:greekLit:tlg5026.msA.chs01", 
            "urn:cts:greekLit:tlg5026.msAint.chs01", 
            "urn:cts:greekLit:tlg5026.msAil.chs01", 
            "urn:cts:greekLit:tlg5026.msAext.chs01"

        ]

        def allActualOnline = ti.allOnline()
        assert (allExpectedOnline as Set) == (allActualOnline as Set)
    }

    @Test void testOnlineNames() {
        def expectedResult = "A_Iliad_p5uc.xml"
        assert ti.onlineDocname(venetusAUrnStr) == expectedResult
        assert ti.onlineDocname(venetusAUrn) == expectedResult
        assert ti.onlineDocname(bogusReference) == null
        assert shouldFail {
            ti.onlineDocname(invalidUrnStr) 
        }
    }

    @Test void testOnlineForWork() {
        def expectedOnline = ["urn:cts:greekLit:tlg0012.tlg001.msA", "urn:cts:greekLit:tlg0012.tlg001.msB"]

        assert ti.onlineForWork(iliadUrnStr) == expectedOnline
        assert ti.onlineForWork(iliadUrn) == expectedOnline
        assert ti.onlineForWork(bogusReference).size() == 0
        assert shouldFail {
            ti.onlineForWork(invalidUrnStr)
        }
    }


    @Test void testOnlineForGroup() {
        def expectedOnline = ["urn:cts:greekLit:tlg0012.tlg001.msA", "urn:cts:greekLit:tlg0012.tlg001.msB"]
        def actualOnline =  ti.onlineForGroup(homerUrn)
        assert expectedOnline.size() == actualOnline.size()
        def firstExpected = expectedOnline[0]
        def firstActual = actualOnline[0]
        assert (expectedOnline as Set) == (actualOnline as Set)


        actualOnline = ti.onlineForGroup(homerUrnStr)
        assert (expectedOnline as Set) == (actualOnline as Set)

        assert ti.onlineForGroup(bogusReference).size() == 0
        assert shouldFail {
            ti.onlineForGroup(invalidUrnStr)
        }
    }

    @Test void testWorkForGroup() {
        def expectedWorks = ["urn:cts:greekLit:tlg0012.tlg002", "urn:cts:greekLit:tlg0012.tlg001"]

        assert ti.worksForGroup(homerUrnStr)  == expectedWorks
        assert ti.worksForGroup(iliadUrnStr)  == expectedWorks
        assert ti.worksForGroup(venetusAUrnStr)  == expectedWorks
        assert ti.worksForGroup(bogusReference).size() == 0
        assert shouldFail{
            ti.worksForGroup(invalidUrnString)
        }
    }

    @Test void testWorkTitle() {
        def expectedTitle = "Iliad"
        assert ti.workTitle(iliadUrn) == expectedTitle
        assert ti.workTitle(iliadUrnStr) == expectedTitle
        assert ti.workTitle(bogusReference) == null
        assert shouldFail {
            ti.workTitle(invalidUrnStr)
        }
    }

    @Test void testWorkLanguages() {
        assert ti.languageForWork(iliadUrnStr) == "grc"
        assert ti.languageForWork(iliadUrn) == "grc"
        assert shouldFail {
	 String bogus = ti.languageForWork(bogusReference) 
	}
        assert shouldFail {
            ti.languageForWork(invalidUrnString)
        }
    }



    @Test void testVersionsForWork() {
        def expectedOnline = ["urn:cts:greekLit:tlg4036.tlg023.chs01", "urn:cts:greekLit:tlg4036.tlg023.chs02"]
        assert ti.versionsForWork(chrestomathyUrn) == expectedOnline
        assert ti.versionsForWork(chrestomathyUrnStr) == expectedOnline
        assert shouldFail {
	  String bogus = ti.versionsForWork(bogusReference).size() 
	}
        assert shouldFail {
            ti.versionsForWork(invalidUrnStr)
        }

    }


    @Test void testEditionLabel() {
        def expectedLabel = "A"
        assert ti.editionLabel(venetusAUrn) == expectedLabel
        assert ti.editionLabel(venetusAUrnStr) == expectedLabel
        assert ti.editionLabel(bogusReference) == null

        assert shouldFail {
            ti.editionLabel(invalidUrnStr)
        }

    }


    @Test void testTranslationLabel() {
        def expectedLabel = "English translation by Blackwell and Dué"
        assert ti.translationLabel(metricalSummariesEnglishUrn) == expectedLabel
        assert ti.translationLabel(metricalSummariesEnglishUrnStr) == expectedLabel
        assert ti.translationLabel(bogusReference) == null
        assert shouldFail {
            ti.translationLabel(invalidUrnStr)
        }
    }

    @Test void testVersionLabel() {
        def expectedOne = "English translation by Blackwell and Dué"
        def expectedTwo = "A"
        assert ti.versionLabel(metricalSummariesEnglishUrn) == expectedOne
        assert ti.versionLabel(metricalSummariesEnglishUrnStr) == expectedOne

        assert ti.versionLabel(venetusAUrn) == expectedTwo
        assert ti.versionLabel(venetusAUrnStr) == expectedTwo

        assert ti.versionLabel(bogusReference) == null
        assert shouldFail {
            ti.versionLabel(invalidUrnStr)
        }
    }


    @Test void testVersionIds() {
        assert ti.typeForVersion(metricalSummariesEnglishUrn) == VersionType.TRANSLATION
        assert ti.typeForVersion(venetusAUrn) == VersionType.EDITION
    }

    @Test void testNsMappings() {
        int expectedNumberMSS = 12
        def mapList = ti.getNsMapList()

        //assert mapList.size() == expectedNumberMSS
        def mapEntry = mapList[venetusAUrnStr]    
        assert mapEntry.size() == 1
        assert mapEntry["tei"] == "http://www.tei-c.org/ns/1.0"
    }

    @Test void testXmlNsMethod() {
        def nsMap = ti.xmlNsForVersion(venetusAUrnStr)
        assert nsMap["tei"] == "http://www.tei-c.org/ns/1.0"
    }

}
