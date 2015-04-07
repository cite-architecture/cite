package edu.harvard.chs.cite


class CiteExtension {

  public String abbr = null
  public String extensionUri =  null
  
  public String rdfType = null
  public String rdfUri = null

  public boolean extendedCitation = null
  
  public Set requestNames = []

  /** Empty constructor. */
  CiteExtension () {
  }


  /** Determines if the object is fully configured.
   * @returns True if all components have been initialized.
   */
  boolean isValid() {
    return (
      (abbr != null) &&
      (extensionUri != null) &&
      (rdfType != null) &&
      (rdfUri != null) &&
      (extendedCitation != null) &&
      (requestNames.size() > 0)
    )
  }

  
}
