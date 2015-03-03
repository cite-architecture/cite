# CTS Text Inventory #

The catalog of texts in a CTS archive records the URNs for every level of the text's CTS work hierarchy, and citation scheme for every level of the text's citation hierarchy.  The `cite` library supports constructing a CTS Text Inventory from an XML serialization validating against the 

@openex@

## Examples ##


From <a href="../../../resources/test/data/testinventory.xml" concordion:set="#ti = setHref(#HREF)">this TextInventory file</a> we can <strong concordion:assertTrue="shouldMakeTi(#ti)">construct a TextInventory</strong>.   

We can verify that URNs at different levels of the work hierarchy are documented in the inventory:

<table
 concordion:execute="#result = urnInInventory(#ti,#urn)">
<tr><th concordion:set="#urn">URN</th><th concordion:assertTrue="#result">In inventory at...</th></tr>
<tr><td>urn:cts:greekLit:tlg0012.tlg001.msA:1.1</td><td>Version level</td></tr>
<tr><td>urn:cts:greekLit:tlg0012.tlg001.msA.lex:1.1.1</td><td>Exemplar level</td></tr>
</table>


@closeex@