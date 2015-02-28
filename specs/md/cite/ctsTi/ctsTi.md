# CTS Text Inventory #

We can use <a href="../../../resources/test/data/testinventory.xml" concordion:set="#ti = setHref(#HREF)">this TextInventory file</a> to <strong concordion:assertTrue="shouldMakeTi(#ti)">construct a TextInventory</strong>.

We can test it with a URN (at the version level), such as  <strong concordion:set="#exemplar">urn:cts:greekLit:tlg0012.tlg001.msA:1.1</strong>, which is a  <strong concordion:assertTrue="isValidURN(#exemplar)">valid CTS URN</strong>:

- We can see that <strong concordion:assertTrue="urnInInventory(#ti, #exemplar)">this URN is documented in the TextInventory.</strong>.

We can test it with a URN (at the exemplar-level), such as  <strong concordion:set="#exemplar">urn:cts:greekLit:tlg0012.tlg001.msA.lex:1.1.1</strong>, which is a  <strong concordion:assertTrue="isValidURN(#exemplar)">valid CTS URN</strong>:

- We can see that <strong concordion:assertTrue="urnInInventory(#ti, #exemplar)">this URN is documented in the TextInventory.</strong>.
