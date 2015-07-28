# CTS Text Inventory #


The TextInventory maintains minimal information about a corpus of texts needed to work with them in the OHCO2 data model.   It can be initialized from an XML serialization validating against the `cite` library's RNG schemas.


@openex@

### Example###





From <a href="../../../resources/test/data/testinventory.xml" concordion:set="#ti = setHref(#HREF)">this TextInventory file</a> we can <strong concordion:assertTrue="shouldMakeTi(#ti)">construct a TextInventory</strong>.   

@closeex@



## Information about the work hierarchy ##



The TextInventory records the URNs for every level of a text's CTS work hierarchy, and includes a human-readable label for the URN at each level.

@openex@

### Examples ###




We can verify that URNs at different levels of the work hierarchy are documented in the inventory:

<table
 concordion:execute="#result = urnInInventory(#ti,#urn)">
<tr><th concordion:set="#urn">URN</th><th concordion:assertTrue="#result">In inventory at...</th></tr>
<tr><td>urn:cts:greekLit:tlg0012.tlg001.msA:1.1</td><td>Version level</td></tr>
<tr><td>urn:cts:greekLit:tlg0012.tlg001.msA.lex:1.1.1</td><td>Exemplar level</td></tr>
</table>


We can retrieve human-readable labels for each level of the work hierarchy:

- group name <strong concordion:set="#groupLevel">urn:cts:greekLit:tlg0012:</strong>  has group name  <strong concordion:assertEquals="groupLabel(#ti, #groupLevel)">Homer</strong>.
- the notional work-level URN <strong concordion:set="#workLevel">urn:cts:greekLit:tlg0012.tlg001:</strong> has the label <em concordion:assertEquals="workLabel(#ti, #workLevel)">Iliad</em>.
- the version-level URN <strong concordion:set="#versionLevel">urn:cts:greekLit:tlg0012.tlg001.msA:</strong> has the label <strong concordion:assertEquals="versionLabel(#ti, #versionLevel)">A</strong>.

@closeex@



### Information about  languages within the work hierarchy ###


For the notional work level of the hierarchy, the TextInventory records the primary language of the text using three-digit ISO language codes.  



@openex@

The *Iliad* (<strong concordion:set="#workLevel">urn:cts:greekLit:tlg0012.tlg001:</strong>) has a primary language code <strong concordion:assertEquals="langCode(#ti,#workLevel)">grc</strong>


The English translation with URN <strong concordion:set="#xlate">urn:cts:greekLit:tlg0012.tlg001.chs01:</strong> has the language code <strong concordion:assertEquals="versionLang(#ti,#xlate)">eng</strong>, while the Venetus A manuscript (<strong concordion:set="#msA">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>) has the code <strong concordion:assertEquals="versionLang(#ti,#msA)">grc</strong>.

@closeex@



## Information about the citation hierarchy ##


The TextInventory documents the citation scheme for every level of the text's citation hierarchy.  


