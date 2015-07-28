package edu.harvard.chs.cite


/**
* A class representing an ORCA analysis relating an analytical
* object to a passage of text.
*/
class Orca {

  CiteUrn analysisRecord
  CtsUrn passageAnalyzed
  CiteUrn analysisObject
  String transformedText
  

  Orca(CiteUrn id, CtsUrn psg, CiteUrn analysis, String xformText) {
    this.analysisRecord = id
    this.passageAnalyzed = psg
    this.analysisObject = analysis
    this.transformedText = xformText
  }
  
}


