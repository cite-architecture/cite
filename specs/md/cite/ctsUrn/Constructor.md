# Constructing CTS URN objects #


The technology-independent representation of a CTS URN as a string of characters is defined by the CTS URN specification.  (See the [github repository for the specification][repo], or an [HTML rendering of the specification][readable].)
You can construct an object representation of a CTS URN from a string conforming to the CTS URN specification. It is an Exception if you try to construct a CTS URN from a String that does not conform to the specification.


[repo]: https://github.com/cite-architecture/ctsurn_spec


[readable]: http://www.homermultitext.org/hmt-docs/specifications/ctsurn/


## Examples ##

- <strong concordion:set="#urn">urn:cts:greekLit:tlg0012.tlg001.msA:1.1</strong> 
conforms to the CTS spec, so we can construct <strong concordion:assertTrue="isValid(#urn)">a valid CTS URN object</strong>.
- <strong concordion:set="#bogus">NOT-A-URN</strong> does not conform to the CTS spec.  Passing it to a constructor <strong concordion:assertFalse="isValid(#bogus)">generates an Exception</strong>.




