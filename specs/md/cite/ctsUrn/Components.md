# Extracting parts of CTS URNs #




From a URN like <code concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</code>, we can extract:.

We can also extract:


- the identifer for its CTS namespace, greekLit
- its text group identifier, either with (greekLit:tlg0012) or without (tlg0012) namespace qualifier
- its work identifier
- its version identifier
- the identifier for its passage node, <strong concordion:assertEquals="getPassage(#point)">2.75</strong>

For a URN citing a range, like <code concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</code>, we can also extract:

- the first node of the range, <strong concordion:assertEquals="getRangeBegin(#range)">1.1</strong>
-  the last node of the range, <strong concordion:assertEquals="getRangeEnd(#range)">2.75</strong>




