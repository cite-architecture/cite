package cite.ctsUrn;

import edu.harvard.chs.cite.CtsUrn;

import org.concordion.integration.junit3.ConcordionTestCase;

public class ComponentsTest extends ConcordionTestCase {

    public boolean isValid(String urnStr) {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

	public String getVersionNoNS(String urnStr)
	throws Exception {
	try{
		CtsUrn urn = new CtsUrn(urnStr);
		return urn.getVersion(false);
	} catch (Exception e) {
		throw e;
	}
	}


	public String getLabelForWorkLevel(String urnStr)
	throws Exception {
	try{
		CtsUrn urn = new CtsUrn(urnStr);
		return urn.labelForWorkLevel();
	} catch (Exception e) {
		throw e;
	}
	}


    public String psgComponent(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getPassageComponent();
	} catch (Exception e) {
	    throw e;
	}
    }    

    
    public String subref(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getSubref();
	} catch (Exception e) {
	    throw e;
	}
    }    

    public Integer subrefidx(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getSubrefIdx();
	} catch (Exception e) {
	    throw e;
	}
    }    









    public String subref1(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getSubref1();
	} catch (Exception e) {
	    throw e;
	}
    }    

    public Integer subrefidx1(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getSubrefIdx1();
	} catch (Exception e) {
	    throw e;
	}
    }    




    public String subref2(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getSubref2();
	} catch (Exception e) {
	    throw e;
	}
    }    

    public Integer subrefidx2(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getSubrefIdx2();
	} catch (Exception e) {
	    throw e;
	}
    }    

    
    
    
    public String versQualified(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getVersion(true);
	} catch (Exception e) {
	    throw e;
	}
    }    


    public String versBare(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getVersion();
	} catch (Exception e) {
	    throw e;
	}
    }

    

    public String wkQualified(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getWork(true);
	} catch (Exception e) {
	    throw e;
	}
    }    


    public String wkBare(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getWork();
	} catch (Exception e) {
	    throw e;
	}
    }

	public String exempQualified(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getExemplar(true);
	} catch (Exception e) {
	    throw e;
	}
    }    

   public String exempBare(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getExemplar(false);
	} catch (Exception e) {
	    throw e;
	}
    }  

    public String tgQualified(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getTextGroup(true);
	} catch (Exception e) {
	    throw e;
	}
    }    


    public String tgBare(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getTextGroup();
	} catch (Exception e) {
	    throw e;
	}
    }    

    
    

    public String ctsNs(String urnStr)
	throws Exception {
	try {
	    CtsUrn urn = new CtsUrn(urnStr);
	    return urn.getCtsNamespace();
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

    
    
}
