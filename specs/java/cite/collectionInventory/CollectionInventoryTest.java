package cite.collectionInventory;


import edu.harvard.chs.cite.CiteUrn;
import edu.harvard.chs.cite.CiteProperty;
import edu.harvard.chs.cite.CiteCollection;
import edu.harvard.chs.cite.CiteExtension;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Arrays;

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




    public boolean isValidExt(String extName, String extUri, String rdfType, String rdfUri, boolean extendCite, String commaList) {
	CiteExtension ce = new CiteExtension();
	ce.abbr = extName;
	ce.extensionUri = extUri;
	
	ce.rdfType =rdfType;
	ce.rdfUri = rdfUri;

	ce.extendedCitation = extendCite;
	
	List<String> items = Arrays.asList(commaList.split(",\\s*"));
	ce.requestNames = new HashSet(items);

	return (ce.isValid());
	
    }


    

    public boolean isValidCollection(String idName, String idType, String idLabel, String labelName, String labelType, String labelLabel, String collUrnStr, String uri, String oneSource) throws Exception {
	CiteCollection cc = new CiteCollection();

	CiteProperty idProp;
	idProp = new CiteProperty(idName,idType,idLabel);
	cc.canonicalIdProp = idProp;
	
	CiteProperty labelPropObj = new CiteProperty(labelName,labelType,labelLabel);
	cc.labelProp = labelPropObj;

	CiteUrn collUrn = new CiteUrn(collUrnStr);
	cc.urn = collUrn;
	cc.nsAbbr = collUrn.getNs();
	cc.nsFull = uri;

	System.err.println("Made CC for " + collUrnStr + " using these settings: ") ;
	System.err.println("idName " + idName);
	System.err.println("idType " + idType);
	System.err.println("idLabel " + idLabel);
	System.err.println("labelName " + labelName);
	System.err.println("uri " + uri);

	ArrayList oneSrc = new ArrayList();
	oneSrc.add("file");
	oneSrc.add(oneSource);
	ArrayList srcs = new ArrayList();
	srcs.add(oneSrc);
	cc.sources = srcs;
	
	return (cc.isValid());

    }



    
    public boolean isValidProp(String n, String t, String l) {
	try {
	    CiteProperty cprop = new CiteProperty(n,t,l);
	    return (true);
	} catch (Exception e) {
	    return(false);
	}
    }


    public boolean isValidControlledProp(String n, String label, String vocab) {
	try {
	    List<String> items = Arrays.asList(vocab.split(",\\s*"));
	    Set vocabSet = new HashSet(items);
	    CiteProperty cprop = new CiteProperty(n,label, vocabSet);
	    return (true);
	} catch (Exception e) {
	    return(false);
	}
    }

}
