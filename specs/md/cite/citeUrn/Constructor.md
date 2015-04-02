# CITE URNs #

The technology-independent representation of a CITE URN as a string of characters is defined by the [CITE URN specification](http://cite-architecture.github.io/citeurn_spec/). 

## Constructing CITE URN objects ##


You can construct an object representation of a CITE URN from a string conforming to the CITE URN specification. It is an Exception if you try to construct a CITE URN from a String that does not conform to the specification.

@openex@

### Examples ###


- The string <strong concordion:set="#coll">urn:cite:hmt:msA</strong> is a valid URN identifying a *collection*, so we can  <strong concordion:assertTrue="isValid(#coll)">construct a URN object</strong> from it.
- The string <strong concordion:set="#urn">urn:cite:hmt:msA.12r</strong> 
is a valid URN identifying a *notional object*, so we can construct <strong concordion:assertTrue="isValid(#urn)">a URN object</strong> from it.
- The string <strong concordion:set="#vers">urn:cite:hmt:msA.12r.1</strong> 
is a valid URN identifying a *version* of an object, so we can construct <strong concordion:assertTrue="isValid(#vers)">a URN object</strong> from it.
- The string <strong concordion:set="#bogus">NOT-A-URN</strong> does not conform to the specification.  Passing it to a constructor <strong concordion:assertFalse="isValid(#bogus)">generates an Exception</strong>.



@closeex@


### Unicode normalization

CITE URN objects can be constructed from strings in any Unicode form, but all output representations of the CITE URN or parts of the CITE URN as strings are normalized to Unicode form NFC.

@openex@

### Examples ###



<table>
<tr>
<th>Input string</th>
<th>Unicode form of input</th>
<th>Length of input in bytes</th>
<th>Output string identical to input string</th>
</tr>


<tr>
<td concordion:set="#srcStr1">urn:cite:examples:tokens.1@μῆνιν</td>
<td concordion:assertEquals="uForm(#srcStr1)">NFC (composed)</td>
<td concordion:assertEquals="getBytes(#srcStr1)">38</td>
<td concordion:assertTrue="matchesOutput(#srcStr1)">Yes</td>
</tr>

<tr>
<td concordion:set="#srcStr2">urn:cite:examples:tokens.1@μῆνιν</td>
<td concordion:assertEquals="uForm(#srcStr2)">NFD (decomposed)</td>
<td concordion:assertEquals="getBytes(#srcStr2)">39</td>
<td concordion:assertFalse="matchesOutput(#srcStr2)">No</td>

</tr>


</table>




@closeex@

