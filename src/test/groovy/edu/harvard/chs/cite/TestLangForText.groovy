package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test

/*
*/
class TestLangForText extends GroovyTestCase {


    File invFile = new File("unittests/data/hmtTI.xml")

    String editionUrn = "urn:cts:greekLit:tlg0012.tlg001.msBm1"
    String translationUrn = "urn:cts:greekLit:tlg5026.met.chs01"

    @Test void testLangVal() {
        TextInventory inv = new TextInventory(invFile)

        assert inv.languageForWork(editionUrn) == "grc"
        assert inv.languageForVersion(editionUrn) == "grc"

        assert inv.languageForWork(translationUrn) == "grc"
        assert inv.languageForVersion(translationUrn) == "eng"

    }


}
