package cite.ctsTi;
import java.io.File;
import edu.harvard.chs.cite.CtsUrn;
import edu.harvard.chs.cite.TextInventory;
import java.text.Normalizer;
import java.text.Normalizer.Form;


import org.concordion.integration.junit3.ConcordionTestCase;

public class CtsTiTest extends ConcordionTestCase {
	
    /** Path in concordion build to Corpus documentation.  Knowing this
     * lets us write markdown docs with relative references to data files
     * in the documentation.*/
    String docPath = "/build/concordion-results/cite/ctsTi/";

    /** Hands back a String parameter so we can save links using concordion's
     * #Href variable for use in later computations. */
    public String setHref(String path) {
	return (path);
    }



    /** Determines wheteher a URN is valid.
     */
    public boolean isValidURN(String urnStr) {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

	/** Creates a TextInventory object. */
    public boolean shouldMakeTi(String ti) {
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    return true;
	} catch (Exception e) {
	    System.err.println ("CtsTiTest: unable to make Text Inventory: " + e.toString());
	    return false;
	}
    }

    /** Test if a URN is in a TextInventory. */
    public boolean urnInInventory(String ti, String urnStr) {
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    CtsUrn urn = new CtsUrn(urnStr);
	    return nti.urnInInventory(urn);
	
	} catch (Exception e) {
	    System.err.println ("CtsTiTest: unable to test if URN is in Inventory: " + e.toString());
	    return false;
	}
    }

    
    public String versionLabel(String ti, String urnStr)
    throws Exception {
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    return nti.versionLabel(urnStr);
	    
	} catch (Exception e) {
	    System.err.println (e.toString());
	    throw (e);
	}
    }



    public String workLabel(String ti, String urnStr)
    throws Exception {
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    return nti.workTitle(urnStr);
	    
	} catch (Exception e) {
	    System.err.println (e.toString());
	    throw (e);
	}
    }




    public String groupLabel(String ti, String urnStr)
    throws Exception {
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    return nti.getGroupName(urnStr);
	    
	} catch (Exception e) {
	    System.err.println (e.toString());
	    throw (e);
	}
    }





    
    public String langCode(String ti, String urnStr)
    throws Exception {
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    return nti.languageForWork(urnStr);
	    
	} catch (Exception e) {
	    System.err.println (e.toString());
	    throw (e);
	}
    }


      public String versionLang(String ti, String urnStr)
    throws Exception {
	  System.err.println ("CtsTiTest: inv " + ti + " and urn " + urnStr);
	try {
	    String buildPath = new java.io.File( "." ).getCanonicalPath() + docPath; 
	    File inv = new File(buildPath + ti);
	    TextInventory nti = new TextInventory ( inv );
	    System.err.println ("CtsTiTest: created TI object, setting debug level");
	    nti.debug = 0;
	    return nti.languageForVersion(urnStr);
	    
	} catch (Exception e) {
	    System.err.println (e.toString());
	    throw (e);
	}
    }
    
    
}
