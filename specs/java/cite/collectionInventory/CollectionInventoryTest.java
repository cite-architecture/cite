package cite.collectionInventory;

import edu.harvard.chs.cite.CiteProperty;



import org.concordion.integration.junit3.ConcordionTestCase;

public class CollectionInventoryTest extends ConcordionTestCase {
	
    /** Path in concordion build to Corpus documentation.  Knowing this
     * lets us write markdown docs with relative references to data files
     * in the documentation.*/
    String docPath = "/build/concordion-results/cite/ctsTi/";

    /** Hands back a String parameter so we can save links using concordion's
     * #Href variable for use in later computations. */
    public String setHref(String path) {
	return (path);
    }



    public boolean isValidProp(String n, String t, String l) {
	try {
	    CiteProperty cprop = new CiteProperty(n,t,l);
	    return (true);
	} catch (Exception e) {
	    return(false);
	}
    }

}
