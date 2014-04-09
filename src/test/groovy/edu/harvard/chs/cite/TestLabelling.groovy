package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


class TestLabelling extends GroovyTestCase {

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




    @Test void testInvAttributes() {
        assert ti.getTiId() == "hmt"
        assert ti.getTiVersion() == "5.0.rc.1"
    }

    @Test void testGroupNames() {
        def expectedLabel = "Homer"
        assert ti.getGroupName(homerUrnStr) == expectedLabel
        assert ti.getGroupName(homerUrn) == expectedLabel
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
}
