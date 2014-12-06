package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;

import org.concordion.integration.junit3.ConcordionTestCase;

public class CtsUrnTest extends ConcordionTestCase {

    public boolean isValid(String urnStr) {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }
    


    /*
    public boolean isRange(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.isRange();
	} catch (Exception e) {
	    throw e;
	}
	}*/


    

    /*
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


    public String getPassage(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getPassageNode();
	} catch (Exception e) {
	    throw e;
	}
    }    


    public String getRangeBegin(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getRangeBegin();
	} catch (Exception e) {
	    throw e;
	}
    }    


    public String getRangeEnd(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getRangeEnd();
	} catch (Exception e) {
	    throw e;
	}
    }    


    public String encoded(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.toString();
	} catch (Exception e) {
	    throw e;
	}
    }    
    */
    
    
}
