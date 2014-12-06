# Extracting parts of CTS URNs #




From a URN like <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong>, we can extract:

- the identifer for its CTS namespace, <strong concordion:assertEquals="ctsNs(#point)">greekLit</strong>
- its text group identifier, either with (<strong concordion:assertEquals="tgQualified(#point)">greekLit:tlg0012</strong>) or without (<strong concordion:assertEquals="tgBare(#point)">tlg0012</strong>) namespace qualifier
- its work identifier, either with (<strong concordion:assertEquals="wkQualified(#point)">greekLit:tlg001</strong>) or without (<strong concordion:assertEquals="wkBare(#point)">tlg001</strong>) namespace qualifier
- its version identifier, either with (<strong concordion:assertEquals="versQualified(#point)">greekLit:msA</strong>) or without (<strong concordion:assertEquals="versBare(#point)">msA</strong>) namespace qualifier
- the identifier for its passage node, <strong concordion:assertEquals="getPassage(#point)">2.75</strong>

For a URN citing a range, like <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</strong>, we can also extract:

- the first node of the range, <strong concordion:assertEquals="getRangeBegin(#range)">1.1</strong>
-  the last node of the range, <strong concordion:assertEquals="getRangeEnd(#range)">2.75</strong>




For a URN with a subreference like <strong concordion:set="#sub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν</strong>, we can extract:

- the whole passage component as submitted to the constructor, <strong concordion:assertEquals="psgComponent(#sub)">1.1@Μῆνιν</strong>
- the subreference, <strong concordion:assertEquals="subref(#sub)">Μῆνιν</strong>, and its implicit index <strong concordion:assertEquals="subrefidx(#sub)">1</strong>.

The semantically equivalent URN  <strong concordion:set="#subidx">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</strong> yields the results:


- passage component, <strong concordion:assertEquals="psgComponent(#subidx)">1.1@Μῆνιν[1]</strong>
- subreference, <strong concordion:assertEquals="subref(#subidx)">Μῆνιν</strong>, and explicit index <strong concordion:assertEquals="subrefidx(#subidx)">1</strong>.


For a range URN with subreferences like <strong concordion:set="#rangesub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]-1.2@οὐλομένην</strong>, we can also extract:

- the first subreference,  <strong concordion:assertEquals="subref1(#rangesub)">Μῆνιν</strong> and its (explicit) index <strong concordion:assertEquals="subrefidx1(#rangesub)">1</strong>
-  the second subreference,  <strong concordion:assertEquals="subref2(#rangesub)">οὐλομένην</strong> and its (implicit) index <strong concordion:assertEquals="subrefidx2(#rangesub)">1</strong>
