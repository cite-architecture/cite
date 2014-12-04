

# Manipulating CTS URNs #

Consider the URN <code concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</code>.

Reduce it to the notional work level:   <strong concordion:assertEquals="reduceToWork(#point)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>.

Remove the passage reference:  <strong concordion:assertEquals="urnWithoutPassage(#point)">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>.


Encode subreference content so URN can be valid in an RDF statement.  The URN
<code concordion:set="#sub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν</code> can be encoded as <strong  concordion:assertEquals="encoded(#sub)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>.  

We can round-trip that to verify that decoding <strong concordion:set="#encoded">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong> yields <strong concordion:assertEquals="decode(#encoded)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</strong>.




