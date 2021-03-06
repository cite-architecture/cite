package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;

import org.concordion.integration.junit3.ConcordionTestCase;

public class ManipulationsTest extends ConcordionTestCase {


    public String reduceToVersion(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.reduceToVersion();
	} catch (Exception e) {
	    throw e;
	}
    }


    
    public String reduceToWork(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.reduceToWork();
	} catch (Exception e) {
	    throw e;
	}
    }

    public String urnWithoutPassage(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getUrnWithoutPassage();
	} catch (Exception e) {
	    throw e;
	}
    }    
    /*
    public String encoded(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.toString(true);
	} catch (Exception e) {
	    throw e;
	}
    }    

    
    public String decode(String encoded)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(encoded);
	    return urn.decode();
	} catch (Exception e) {
	    throw e;
	}
    }    
    */

    
}
