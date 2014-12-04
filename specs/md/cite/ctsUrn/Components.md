# Extracting parts of CTS URNs #




Consider the URN <code concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</code>.

The passage node is <strong concordion:assertEquals="getPassage(#point)">2.75</strong>.

You can find the ends of a range  like
<code concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</code>:

- the first node is <strong concordion:assertEquals="getRangeBegin(#range)">1.1</strong>
-  the last node is <strong concordion:assertEquals="getRangeEnd(#range)">2.75</strong>




