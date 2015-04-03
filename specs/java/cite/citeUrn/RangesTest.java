package cite.citeUrn;

import edu.harvard.chs.cite.CiteUrn;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.concordion.integration.junit3.ConcordionTestCase;

public class RangesTest extends ConcordionTestCase {


    public boolean isRange(String urnStr)
    throws Exception {
	CiteUrn urn = new CiteUrn(urnStr);
	return urn.isRange();
    }


    public String firstObj(String urnStr)
    throws Exception {
	CiteUrn urn = new CiteUrn(urnStr);
	return urn.getFirstObject();
    }

    public String secondObj(String urnStr)
    throws Exception {
	CiteUrn urn = new CiteUrn(urnStr);
	return urn.getSecondObject();
    }





    public String firstExtended(String urnStr)
    throws Exception {
	CiteUrn urn = new CiteUrn(urnStr);
	String extended =  urn.getFirstExtended();
	if (extended == null) {
	    return ("null");
	} else {
	    return (extended);
	}
    }

    public String secondExtended(String urnStr)
    throws Exception {
	CiteUrn urn = new CiteUrn(urnStr);
	String extended =  urn.getSecondExtended();
	if (extended == null) {
	    return ("null");
	} else {
	    return (extended);
	}
    }



    
}
