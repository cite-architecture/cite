package cite.citeUrn;

import edu.harvard.chs.cite.CiteUrn;


import org.concordion.integration.junit3.ConcordionTestCase;

public class CiteUrnTest extends ConcordionTestCase {


    public boolean isValid(String urnStr) {
	try {
	    CiteUrn urn = new CiteUrn(urnStr);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    
}
