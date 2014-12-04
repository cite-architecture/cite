package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;

import org.concordion.integration.junit3.ConcordionTestCase;

public class ContentsTest extends ConcordionTestCase {



    public boolean isRange(String urnStr)
    throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.isRange();
	} catch (Exception e) {
	    throw e;
	}
    }

    
}
