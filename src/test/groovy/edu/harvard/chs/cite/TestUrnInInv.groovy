package edu.harvard.chs.cite 

import static org.junit.Assert.*
import org.junit.Test

/*
*/
class TestUrnInInv extends GroovyTestCase {


    File invFile = new File("testdata/Inventory.xml")
    CtsUrn editionUrn = new CtsUrn("urn:cts:greekLit:tlg0031.tlg001.fugnt001")
    CtsUrn workUrn = new CtsUrn("urn:cts:greekLit:tlg0031.tlg001")
    CtsUrn groupUrn = new CtsUrn("urn:cts:greekLit:tlg0031")

    CtsUrn fakeUrn = new CtsUrn("urn:cts:greekLit:fake.urn")


    @Test void testUrnInInv() {
        TextInventory inv = new TextInventory(invFile)
        assert inv.urnInInventory(workUrn) 
        assert inv.urnInInventory(editionUrn) 
        assert inv.urnInInventory(groupUrn) 
        shouldFail {
            assert inv.urnInInventory(fakeUrn)
        }

    }


}
