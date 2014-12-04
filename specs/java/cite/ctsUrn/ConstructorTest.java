package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;

import org.concordion.integration.junit3.ConcordionTestCase;

public class ConstructorTest extends ConcordionTestCase {

    public boolean isValid(String urnStr) {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    
}
