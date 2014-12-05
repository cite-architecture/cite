


# Determining the contents of a CTS URN #




Determine if a CTS URN represents a range or not:

- the URN <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</strong> is a <strong concordion:assertTrue="isRange(#range)">range</strong>.

- the URN <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong> is <strong concordion:assertFalse="isRange(#point)">not a range</strong>.


Find the level of the URN's work hierarchy:

- the  URN <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA.tokens:1.1-2.75</strong> is cited at the <strong concordion:assertEquals="getDepthLabel(#range)">exemplar</strong> level.
- the  URN <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</strong> is cited at the <strong concordion:assertEquals="getDepthLabel(#range)">version</strong> level.
- the URN  <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001:1.1-2.75</strong> is cited at the <strong concordion:assertEquals="getDepthLabel(#range)">work</strong> level.

Find the depth of the URN's passage citation:

- the  URN <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001:1.1-2.75</strong> is cited at  <strong concordion:assertEquals="getDepth(#range)">2</strong> levels (book and line of the *Iliad*).
- the  URN <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001:1</strong> is cited at  <strong concordion:assertEquals="getDepth(#range)">1</strong> level (book of the *Iliad*).




