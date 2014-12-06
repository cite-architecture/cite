

# Manipulating CTS URNs #

We can manipulate URN like <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong> to:

- reduce it to the notional work level,   <strong concordion:assertEquals="reduceToWork(#point)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>
- remove the passage reference, <strong concordion:assertEquals="urnWithoutPassage(#point)">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>


For a URN with exemplar, such as  <strong concordion:set="#exemplar">urn:cts:greekLit:tlg0012.tlg001.msA.lex:2.75</strong>, we can:

- reduce it to the version level,   <strong concordion:assertEquals="reduceToVersion(#exemplar)">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong>

