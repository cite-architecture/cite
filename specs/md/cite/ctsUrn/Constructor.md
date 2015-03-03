# Constructing CTS URN objects #




The technology-independent representation of a CTS URN as a string of characters is defined by the [CTS URN specification][readable]. 
You can construct an object representation of a CTS URN from a string conforming to the CTS URN specification. It is an Exception if you try to construct a CTS URN from a String that does not conform to the specification.


[repo]: https://github.com/cite-architecture/ctsurn_spec


[readable]: http://cite-architecture.github.io/ctsurn_spec/

@openex@

### Examples ###



- The string <strong concordion:set="#urn">urn:cts:greekLit:tlg0012.tlg001.msA:1.1</strong> 
conforms to the CTS spec, so we can construct <strong concordion:assertTrue="isValid(#urn)">a valid CTS URN object</strong>.
- The string <strong concordion:set="#bogus">NOT-A-URN</strong> does not conform to the CTS spec.  Passing it to a constructor <strong concordion:assertFalse="isValid(#bogus)">generates an Exception</strong>.


@closeex@


@openex@

### Less obvious examples ###

**Invalid index on subreference**:  substrings are indexed with positive integers. The string
<strong concordion:set="#badidx">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[0]</strong> is therefore invalid.
Passing it to a constructor <strong concordion:assertFalse="isValid(#badidx)">generates an Exception</strong>.


@closeex@





## Unicode normalization ##


CTS URN objects can be constructed from strings in any Unicode form, but all output representations of the CTS URN or parts of the CTS URN as strings are normalized to Unicode form NFC.


@openex@

### Examples ###

Compare the two CTS URNs in this table:  they look identical when printed, but one is in pre-composed form (Unicode form NFC), the other in decomposed form (Unicode form NFD).

<table>
<tr>
<th>Input string</th>
<th>Unicode form of input</th>
<th>Length of input in bytes</th>
<th>Output string identical to input string</th>
</tr>


<tr>
<td concordion:set="#srcStr">urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν</td>
<td concordion:assertEquals="uForm(#srcStr)">NFC (composed)</td>
<td concordion:assertEquals="getBytes(#srcStr)">47</td>
<td concordion:assertTrue="matchesOutput(#srcStr)">Yes</td>
</tr>

<tr>
<td concordion:set="#srcStr">urn:cts:greekLit:tlg0012.tlg001:1.1@μῆνιν</td>
<td concordion:assertEquals="uForm(#srcStr)">NFD (decomposed)</td>
<td concordion:assertEquals="getBytes(#srcStr)">48</td>
<td concordion:assertFalse="matchesOutput(#srcStr)">No</td>

</tr>


</table>


@closeex@