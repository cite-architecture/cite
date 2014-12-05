

# Manipulating CTS URNs #

We can manipulate URN like <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong> to:

- reduce it to the notional work level,   <strong concordion:assertEquals="reduceToWork(#point)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>
- remove the passage reference, <strong concordion:assertEquals="urnWithoutPassage(#point)">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>


For a URN with exemplar, such as  <strong concordion:set="#exemplar">urn:cts:greekLit:tlg0012.tlg001.msA.lex:2.75</strong>, we can:

- reduce it to the version level,   <strong concordion:assertEquals="reduceToVersion(#exemplar)">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong>

In a URN with a subreference <code concordion:set="#sub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</code>, we can URI-encode subreference content to get an equivalent string guaranteed to be valid in an RDF statement.   When CTS URNs are either encoded or decoded, implict indexed on the subreference are made explicit.  Its URI-encoded value:  

- <strong  concordion:assertEquals="encoded(#sub)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>


The semantically equivalent URN
<code concordion:set="#implied">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν</code> can therefore be encoded to the same value:

-  <strong  concordion:assertEquals="encoded(#implied)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong>

If this string <strong concordion:set="#encoded">urn:cts:greekLit:tlg0012.tlg001.msA:1.1%40%CE%9C%E1%BF%86%CE%BD%CE%B9%CE%BD%5B1%5D</strong> is in turn decoded, its value is also a valide URN with explicit subreference index:

-  <strong concordion:assertEquals="decode(#encoded)">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</strong>




Here's a wacky one to convert: a range with sub references:

<strong concordion:set="#rangesub">urn:cts:greekLit:tlg4036.tlg023.msA_tokens:Homer.11@Ἡσίοδος-Homer.11@Ὅμηρον</strong> 

is encoded as

<strong concordion:assertEquals="encoded(#rangesub)">urn:cts:greekLit:tlg4036.tlg023.msA_tokens:Homer.11%40%CE%97%CC%94%CF%83%CE%B9%CC%81%CE%BF%CE%B4%CE%BF%CF%82%5B1%5D-Homer.11%40%CE%9F%CC%94%CC%81%CE%BC%CE%B7%CF%81%CE%BF%CE%BD%5B1%5D</strong>

and round tripping <strong concordion:set="#rangenc">urn:cts:greekLit:tlg4036.tlg023.msA_tokens:Homer.11%40%CE%97%CC%94%CF%83%CE%B9%CC%81%CE%BF%CE%B4%CE%BF%CF%82%5B1%5D-Homer.11%40%CE%9F%CC%94%CC%81%CE%BC%CE%B7%CF%81%CE%BF%CE%BD%5B1%5D</strong> yields 

<strong concordion:assertEquals="decode(#rangenc)">urn:cts:greekLit:tlg4036.tlg023.msA_tokens:Homer.11@Ἡσίοδος[1]-Homer.11@Ὅμηρον[1]</strong>

