package edu.harvard.chs.cite


/**
*
*/
class CiteUrn {
      // All member properties are initialized in constructor,
      // so make them final:
      // the whole URN:
      final String asString


      final String ns
      final String collection
      final String objectId
      final String extendedRef
      final String version

      /** CiteUrns are constructed from a String conforming to the
      * syntax and semantics of the draft CTS URN proposal.
      */
    CiteUrn (String urnStr) {
        def components = urnStr.split(/:/)
        boolean syntaxOk = true
        if (components.size() != 4) {
            syntaxOk =  false
        }
        if (components[0] != 'urn') {
            syntaxOk = false
        }
        if (components[1] != 'cite') {
            syntaxOk = false
        }

        if (syntaxOk) {
            this.asString = urnStr
            this.ns = components[2]
            String wholeRef = components[3]
            def refParts = wholeRef.split(/@/)
            if (refParts.size() == 2) {
                this.extendedRef = refParts[1]
                wholeRef = refParts[0]
            }
            def idparts = wholeRef.split(/\./)
            switch (idparts.size()) {
                case 1:
                    this.collection = idparts[0]
                break
                case 2:
                    this.collection = idparts[0]
                this.objectId = idparts[1]
                break
                case 3:
                    this.collection = idparts[0]
                this.objectId = idparts[1]
                this.version = idparts[2]
                break

                default :
                    throw new Exception("Too many dot-separated parts in id component: ${components[3]}")
                break
            }

        } else {
            throw new Exception("Bad URN syntax: #${urnStr}#")
        }
    }
      
  /**
   * Returns the CITE URN object as a String in the notation defined by
   * the proposed CITE URN standard.
   * @returns The URN as a String.
   */
  String toString() {
    return asString
  }





    String getNs() {
        return this.ns
    }
    String getCollection() {
        return this.collection
    }
    String getObjectId() {
        return this.objectId
    }
    String getVersion() {
        return this.version
    }
    String getExtendedRef() {
        return this.extendedRef
    }

    boolean hasVersion() {
        return (this.version != null)
    } 

    boolean hasObjectId() {
        return (this.objectId != null)
    } 


    
    /** Creates a CiteUrn identifying a Collection from
    * a CiteUrn at any level.
    * @param u The CiteUrn in question.
    * @returns A CiteUrn identifying a Collection.
    */
    CiteUrn collectionForUrn() {
        return (new CiteUrn("urn:cite:${this.getNs()}:${this.getCollection()}"))
    }
}
