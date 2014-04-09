package edu.harvard.chs.cite 


import static org.junit.Assert.*
import org.junit.Test


/** Class to test cite library's CtsUrn class. 
*/
class TestCtsUrnWorkComponent extends GroovyTestCase {





  void testWorkLevel() {
    String groupStr = "urn:cts:latinLit:stoa0115"
    CtsUrn groupUrn = new CtsUrn(groupStr)
    assert groupUrn.getWorkLevel().toString() == "GROUP"

    String workStr = "urn:cts:latinLit:stoa0115.stoa002:preface.1"
    CtsUrn workUrn = new CtsUrn(workStr)
    assert workUrn.getWorkLevel().toString() == "WORK"
    
    String versionStr = "urn:cts:latinLit:stoa0115.stoa002.stoa01"
    CtsUrn versionUrn = new CtsUrn(versionStr)
    assert versionUrn.getWorkLevel().toString() == "VERSION"

    String exemplarStr = "urn:cts:latinLit:stoa0115.stoa002.stoa01.tokenized"
    CtsUrn exemplarUrn = new CtsUrn(exemplarStr)
    assert exemplarUrn.getWorkLevel().toString() == "EXEMPLAR"
  }



  void testGroup() {
    String groupStr = "urn:cts:greekLit:tlg0012"
    CtsUrn groupUrn = new CtsUrn(groupStr)
    assert groupUrn.getTextGroup() == "tlg0012"

    shouldFail {
      String noWork = groupUrn.getWork()
    }
    shouldFail {
      String noWork = groupUrn.getWork(true)
    }

   shouldFail {
     String noVersion = groupUrn.getVersion()
   }
   shouldFail {
     String noVersion = groupUrn.getVersion(true)
   }


   shouldFail {
     String noExemplar = groupUrn.getExemplar()
   }
   shouldFail {
     String noVersion = groupUrn.getExemplar(true)
   }

  }

  void testGroupAndWork() {
    
    String testUrnStr = "urn:cts:greekLit:tlg1220.tlg001:1" 
    CtsUrn urn = new CtsUrn(testUrnStr)

    assert urn.getTextGroup(true) == "greekLit:tlg1220"
    assert urn.getWork(true) == "greekLit:tlg001"

    assert urn.getTextGroup(false) == "tlg1220"
    assert urn.getWork(false) == "tlg001"

    assert urn.getTextGroup() == "tlg1220"
    assert urn.getWork() == "tlg001"


    shouldFail {
      String noVersion = urn.getVersion()
    }

    shouldFail {
      String noVersion = urn.getVersion(true)
    }

    shouldFail {
      String noExemplar = urn.getExemplar()
    }

    shouldFail {
      String noExemplar = urn.getExemplar(true)
    }

  }


  void testVersion() {
    String versionUrnStr = "urn:cts:greekLit:tlg0012.tlg001.msA:1.10"
    CtsUrn urn = new CtsUrn(versionUrnStr)

    assert urn.getVersion(true) == "greekLit:msA"
    assert urn.getVersion(false) == "msA"
    assert urn.getVersion() == "msA"



    shouldFail {
      String noExemplar = urn.getExemplar()
    }

    shouldFail {
      String noExemplar = urn.getExemplar(true)
    }
  }

  void testExemplar() {
    String tokenUrnStr = "urn:cts:greekLit:tlg0012.tlg001.msA.tokens:1.10.2"
    CtsUrn urn = new CtsUrn(tokenUrnStr)
    assert urn.getExemplar() == "tokens"
    assert urn.getExemplar(false) == "tokens"
    assert urn.getExemplar(true) == "greekLit:tokens"
  }

}
