package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test


/** Class to test the cite library's TextInventory class. 
*/
class TestTIdata extends GroovyTestCase {

    String testTextInvDir = "unittests/data/tierrors"
    File testDir = new File(testTextInvDir)

    @Test void testConstructor() {
        TextInventory goodInv = new TextInventory(new File(testDir, "good.xml"))
        assert goodInv

        def msg = shouldFail {
            TextInventory badWork = new TextInventory(new File(testDir, "badUrnForWork.xml"))
        }
        // Inspect msg:
        System.err.println "Failed on bad work with : " + msg


        def versmsg = shouldFail {
            TextInventory badVersion = new TextInventory(new File(testDir, "badUrnForVersion.xml"))
        }
        System.err.println "Failed on bad version with " + versmsg

        def langmsg = shouldFail {
            TextInventory badLang = new TextInventory(new File(testDir, "badLang.xml"))
        }
        System.err.println "Failed on bad lang. attr. with " + langmsg




        def xmlnsmsg = shouldFail {
            TextInventory badXmlNs = new TextInventory(new File(testDir, "badXMLNS.xml"))
        }
        System.err.println "Failed on bad xml ns with " + xmlnsmsg


    }

}
