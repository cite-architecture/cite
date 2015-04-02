package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.concordion.integration.junit3.ConcordionTestCase;

public class ConstructorTest extends ConcordionTestCase {

    public boolean isValid(String urnStr) {
	try {
	    Integer debug = 0;
	    CtsUrn urn = new CtsUrn(urnStr,debug);

	    return true;
	} catch (Exception e) {
	    return false;
	}
    }


    public boolean matchesOutput(String urnStr) {
	Integer debug = 0;
	if (debug > 0) {
	    System.err.println ("Look for  matching urn from input " + urnStr);
	}

	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    byte[] srcBytes = urn.toString().getBytes("UTF-8");
	    if (debug > 0) {
		System.err.println ("Src string in bytes: " + srcBytes.length);
		System.err.println ("NFC? " + Normalizer.isNormalized(urnStr, Normalizer.Form.NFC));
	    }
	    byte[] urnBytes = urn.toString().getBytes("UTF-8");
	    if (debug > 0) {
		System.err.println ("Urn string in bytes: " + urnBytes.length);
		System.err.println ("NFC? " + Normalizer.isNormalized(urn.toString(), Normalizer.Form.NFC));
	    }
	    return urnStr.equals(urn.toString());
	    
	} catch (Exception e) {
	    System.err.println ("Failed to make comparison.");
	    System.err.print(e);
	    return false;
	}
    }


    
    public String uForm(String str)
    throws Exception {
	if (Normalizer.isNormalized(str, Normalizer.Form.NFC)) {
	    return ("NFC (composed)");
	} else 	if (Normalizer.isNormalized(str, Normalizer.Form.NFD)) {
	    return ("NFD (decomposed)");

	} else 	if (Normalizer.isNormalized(str, Normalizer.Form.NFKC)) {
	    return ("NFKC");

	} else 	if (Normalizer.isNormalized(str, Normalizer.Form.NFKD)) {
	    return ("NFKD");
	    
	}  else  {
	    throw new Exception("Unknown Unicode normalization form.");
	}
    }
    
    public Integer getBytes(String str)
	throws Exception {
	try {
	    byte[] byteArray = str.getBytes("UTF-8");
	    return byteArray.length;
	    
	} catch (Exception e) {
	    System.err.println ("UNable to get bytearray for " + str);
	    throw e;
	    }
    }
    
}
