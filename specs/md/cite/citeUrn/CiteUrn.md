# CITE URNs #

The technology-independent representation of a CITE URN as a string of characters is defined by the [CITE URN specification](http://cite-architecture.github.io/citeurn_spec/). 

## Constructing CITE URN objects ##


You can construct an object representation of a CITE URN from a string conforming to the CITE URN specification. It is an Exception if you try to construct a CITE URN from a String that does not conform to the specification.

@openex@

### Examples ###


- The string <strong concordion:set="#urn">urn:cite:hmt:msA.12r</strong> 
conforms to the specification, so we can construct <strong concordion:assertTrue="isValid(#urn)">a valid URN object</strong>.
- The string <strong concordion:set="#bogus">NOT-A-URN</strong> does not conform to the specification.  Passing it to a constructor <strong concordion:assertFalse="isValid(#bogus)">generates an Exception</strong>.
- 
@closeex@


### Unicode normalization

CITE URN objects can be constructed from strings in any Unicode form, but all output representations of the CITE URN or parts of the CITE URN as strings are normalized to Unicode form NFC.

@openex@

### Examples ###


@closeex@

