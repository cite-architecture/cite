

# Manipulating CTS URNs #

In addition to [extracting component parts](Components.html) of a URN, you can manipulate those components to create valid new URNs.  New URNs necessarily have a different meaning, but because `cite` facilitates building URNs from the components of another URN, semantically related URNs can be a automatically assembled.

## Supported manipulations

1. To refer to the cited text as a whole, we can remove the passage component of a URN.
2. To generalize a URN referring to a version or exemplar, we can reduce the work component to the notional work level, while keeping the passage reference unchanged.
3. To generalize a URN referring to a specific exemplar, we can reduce the work component to the version level (edition or translation), while keeping the passage reference unchanged.

@openex@


### Example: version-level URN ###


For the version-level URN <strong concordion:set="#point">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong>:


1. referring to the *cited version without the passage* reference yields <strong concordion:assertEquals="urnWithoutPassage(#point)">urn:cts:greekLit:tlg0012.tlg001.msA:</strong>
2. generalizing it to the *notional work* level yields  <strong concordion:assertEquals="reduceToWork(#point)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>


@closeex@


@openex@
### Example: exemplar-level URN ###

For the exemplar-level URN <strong concordion:set="#exemplar">urn:cts:greekLit:tlg0012.tlg001.msA.lex:2.75</strong>:


1. referring to the *cited version without the passage* reference yields <strong concordion:assertEquals="urnWithoutPassage(#exemplar)">urn:cts:greekLit:tlg0012.tlg001.msA.lex:</strong>. (Note required trailing colon.)
2. generalizing it to the *notional work* level yields  <strong concordion:assertEquals="reduceToWork(#exemplar)">urn:cts:greekLit:tlg0012.tlg001:2.75</strong>
3. generalizing it to the *version level* yields  <strong concordion:assertEquals="reduceToVersion(#exemplar)">urn:cts:greekLit:tlg0012.tlg001.msA:2.75</strong>




@closeex@