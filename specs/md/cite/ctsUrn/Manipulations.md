

# Manipulating CTS URNs #

Consider the URN <code concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</code>.

Reduce it to the notional work level:   <strong concordion:assertEquals="reduceToWork(#point)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>.

Remove the passage reference:  <strong concordion:assertEquals="urnWithoutPassage(#point)">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>.

In a URN with subreference, subreference content can be URI-encoded to ensure that the URN with subreference will be valid in an RDF statement.   When CTS URNs are either encoded or decoded, implict indexed on the subreference are made explicit.

## Examples ##


The URN
<code concordion:set="#sub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</code> can be encoded as <strong  concordion:assertEquals="encoded(#sub)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>.  


The semantically equivalent URN
<code concordion:set="#implied">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν</code> can be encoded to the same value <strong  concordion:assertEquals="encoded(#implied)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>.  

If this string <strong concordion:set="#encoded">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong> is decoded, its value is <strong concordion:assertEquals="decode(#encoded)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</strong>.




