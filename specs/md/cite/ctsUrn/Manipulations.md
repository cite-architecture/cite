

# Manipulating CTS URNs #

We can manipulate URN like <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong> to:

- reduce it to the notional work level,   <strong concordion:assertEquals="reduceToWork(#point)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>
- remove the passage reference, <strong concordion:assertEquals="urnWithoutPassage(#point)">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>

In a URN with a subreference <code concordion:set="#sub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</code>, we can URI-encode subreference content to get an equivalent string guaranteed to be valid in an RDF statement.   When CTS URNs are either encoded or decoded, implict indexed on the subreference are made explicit.  Its URI-encoded value:  

- <strong  concordion:assertEquals="encoded(#sub)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>


The semantically equivalent URN
<code concordion:set="#implied">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν</code> can therefore be encoded to the same value:

-  <strong  concordion:assertEquals="encoded(#implied)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>

If this string <strong concordion:set="#encoded">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong> is in turn decoded, its value is also a valide URN with explicit subreference index:

-  <strong concordion:assertEquals="decode(#encoded)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</strong>




