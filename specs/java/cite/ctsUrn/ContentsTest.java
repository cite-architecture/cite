package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;

import org.concordion.integration.junit3.ConcordionTestCase;

public class ContentsTest extends ConcordionTestCase {



    public boolean hasSubref(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.hasSubref();
	} catch (Exception e) {
	    throw e;
	}
    }


        public boolean hasSubref1(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.hasSubref1();
	} catch (Exception e) {
	    throw e;
	}
    }


        public boolean hasSubref2(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.hasSubref2();
	} catch (Exception e) {
	    throw e;
	}
    }



    
    public boolean isRange(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.isRange();
	} catch (Exception e) {
	    throw e;
	}
    }


    public Integer getDepth(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getCitationDepth();
	} catch (Exception e) {
	    throw e;
	}
    }


        public String getDepthLabel(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.labelForWorkLevel();
	} catch (Exception e) {
	    throw e;
	}
    }

}
