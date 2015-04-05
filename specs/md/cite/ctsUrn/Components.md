# Extracting parts of CTS URNs #

The [CTS URN specification](http://cite-architecture.github.io/ctsurn_spec/) defines both syntax and semantics of a CTS URN.  Because each component has a specified meaning, the `cite` library supports directly extracting individual parts.


### Directly addressable parts of a CTS URN ###

1. The identifier for its *CTS namespace*, which defines the meaning for all other components of the URN.
2. The parts of the *work component*. Because the meaning of the work component is specific to a particular CTS namespace, the parts of the work component may be extracted either with the qualifying CTS namespace, or without the CTS namespace for use in contexts where the namespace is already defined.  These parts are:
    2. the identifier for its text group
    3. the identifier for its work at a notional level
    4. the identifier for its specific version (edition or translation)
    5. the identifier for an individual exemplar of a version
3. The parts of the *passage reference component*:
    4. the passage component as a whole
    5. the first and last nodes of a range
    6. any subreferences, and their index (explicit or implicit).  See details in the following section.


@openex@

### Example: Different Work-level URNs

A URN can cite a work at the EXEMPLAR, VERSION, WORK, or GROUP level.

The URN <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001:2.75</strong> cites at the <strong concordion:assertEquals="getLabelForWorkLevel(#point)">work</strong> level.

The URN <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong> cites at the <strong concordion:assertEquals="getLabelForWorkLevel(#point)">version</strong> level.

The URN <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA.lex:2.75</strong> cites at the <strong concordion:assertEquals="getLabelForWorkLevel(#point)">exemplar</strong> level.

@closeex@

@openex@

### Example: version-level URN citing a single node ###


Components of the URN <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong>.

1. its **CTS namespace** is <strong concordion:assertEquals="ctsNs(#point)">greekLit</strong>
2. parts of the work component:
    1. its namespace-qualified **text group** identifier is <strong concordion:assertEquals="tgQualified(#point)">greekLit:tlg0012</strong>, or without namespace is <strong concordion:assertEquals="tgBare(#point)">tlg0012</strong>
    2. its namespace-qualified **work** identifier  is <strong concordion:assertEquals="wkQualified(#point)">greekLit:tlg001</strong>, or without namespace is <strong concordion:assertEquals="wkBare(#point)">tlg001</strong>
    3. its namespace-qualified **version** identifier is <strong concordion:assertEquals="versQualified(#point)">greekLit:msA</strong>, or without namespace is <strong concordion:assertEquals="versBare(#point)">msA</strong>
3. the passage component: 
    1. the entire **passage component** is <strong concordion:assertEquals="getPassage(#point)">2.75</strong>

@closeex@


@openex@

### Example:  exemplar-level URN ###


Components of the URN <strong concordion:set="#exemplarUrn">urn:cts:greekLit:tlg0012.tlg001.msA.lex:1.1.1</strong>.

1. its **CTS namespace** is <strong concordion:assertEquals="ctsNs(#exemplarUrn)">greekLit</strong>
2. parts of the work component:
    1. its namespace-qualified **text group** identifier is <strong concordion:assertEquals="tgQualified(#exemplarUrn)">greekLit:tlg0012</strong>, or without namespace is <strong concordion:assertEquals="tgBare(#exemplarUrn)">tlg0012</strong>
    2. its namespace-qualified **work** identifier is <strong concordion:assertEquals="wkQualified(#exemplarUrn)">greekLit:tlg001</strong>, or without namespace is <strong concordion:assertEquals="wkBare(#exemplarUrn)">tlg001</strong>
    3. its namespace-qualified **version** identifier is <strong concordion:assertEquals="versQualified(#exemplarUrn)">greekLit:msA</strong>, or without namespace is <strong concordion:assertEquals="versBare(#exemplarUrn)">msA</strong>
    4.  its namespace-qualified **exemplar** identifier is <strong concordion:assertEquals="exempQualified(#exemplarUrn)">greekLit:lex</strong>, or without namespace is <strong concordion:assertEquals="exempBare(#exemplarUrn)">lex</strong>
3. the passage component: 
    1. the entire **passage component** is <strong concordion:assertEquals="getPassage(#point)">2.75</strong>

@closeex@

@openex@

### Example: citing a range ###


For the URN <strong concordion:set="#range">urn:cts:greekLit:tlg0012.tlg001.msA:1.1-2.75</strong>, the CTS namespace and parts of the work component are identical to the previous examples. Parts of the passage component:

1. the passage component as a whole is  <strong concordion:assertEquals="psgComponent(#range)">1.1-2.75</strong>
2. the first node of the range is <strong concordion:assertEquals="getRangeBegin(#range)">1.1</strong>
3. the last node of the range is <strong concordion:assertEquals="getRangeEnd(#range)">2.75</strong>


@closeex@


### Subreferences ###

CTS URNs express references to units in a canonical citation system that is specific to individual texts.  For specific versions or exemplars, the notation permits pointing to indexed substrings within the text content of the cited passage.  Substrings are indexed beginning from **1** (not **0**), and a substring reference with no explicit index is defined to mean that is has an implied index of 1.

The `cite` library's methods support working with subreferences as follows:

1. extracting the entire passage component returns the *literal string version of passage component as submitted to the CtsUrn constructor*.  If an index of 1 is left implicit, it is not supplied.
2. extracting the substring returns the substring without index.
3. extracting the index value returns an integer > 0, whether the index was expressed or implied


 For ranges, `cite` supports extracting substring and index values for either end of the range.


@openex@

### Example: subreference with implicit index



Subreference content of the URN  <strong concordion:set="#sub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν</strong>.

1. the passage component as a whole is  <strong concordion:assertEquals="psgComponent(#sub)">1.1@Μῆνιν</strong>
2. the substring value is <strong concordion:assertEquals="subref(#sub)">Μῆνιν</strong>
3.  the (implicit) index value <strong concordion:assertEquals="subrefidx(#sub)">1</strong>.

@closeex@

@openex@

### Example: subreference with explicit index

The  URN  <strong concordion:set="#subidx">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]</strong> is semantically equivalent to the previous example

1. the passage component as a whole is <strong concordion:assertEquals="psgComponent(#subidx)">1.1@Μῆνιν[1]</strong>
2.  the substring value is <strong concordion:assertEquals="subref(#subidx)">Μῆνιν</strong>
3.   the (explicit) index value is <strong concordion:assertEquals="subrefidx(#subidx)">1</strong>.

@closeex@



@openex@

### Example:  subreferences on a range


Ssubreference content of the URN <strong concordion:set="#rangesub">urn:cts:greekLit:tlg0012.tlg001.msA:1.1@Μῆνιν[1]-1.2@οὐλομένην</strong>.


1. the passage component as a whole is <strong concordion:assertEquals="psgComponent(#rangesub)">1.1@Μῆνιν[1]-1.2@οὐλομένην</strong>
2. the substring value for the first subreference is <strong concordion:assertEquals="subref1(#rangesub)">Μῆνιν</strong>, and for the second subreferences is <strong concordion:assertEquals="subref2(#rangesub)">οὐλομένην</strong>
3. the (explict) index for the first subreference is <strong concordion:assertEquals="subrefidx1(#rangesub)">1</strong>, and for  the second (implicit) subreference is <strong concordion:assertEquals="subrefidx2(#rangesub)">1</strong>


@closeex@



