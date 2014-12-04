


# Determining the contents of a CTS URN #




Determine if a CTS URN represents a range or not:

the URN <code concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</code> is a <strong concordion:assertTrue="isRange(#range)">range</strong>.

the URN <code concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</code> is <strong concordion:assertFalse="isRange(#point)">not a range</strong>.
.

