# Constructing CTS URNs #


The technology-indepdent representation of a CTS URN as a string of characters is defined by the CTS URN specification.  (See the [github repository for the specification][repo], or an [HTML rendering of the specification][readable].)
You can construct an object representation of a CTS URN from a string conforming to the CTS URN specification. It is an Exception if you try to construct a CTS URN from a String that does not conform to the specification.


[repo]: https://github.com/cite-architecture/ctsurn_spec


[readable]: http://www.homermultitext.org/hmt-docs/specifications/ctsurn/


## Examples ##

The string 

><code concordion:set="#urn">urn:cts:greekLit:tlg0012.tlg001.msA:1.1</code> 


conforms to the CTS spec, so it <strong concordion:assertTrue="isValid(#urn)">is valid</strong>.

The string 

><code concordion:set="#bogus">NOT_A_URN</code> 


does not conform to the CTS spec, so it is <strong concordion:assertFalse="isValid(#bogus)">not valid</strong>.




