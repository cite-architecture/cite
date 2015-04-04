# URNs citing ranges #

The `cite` library can determine if a CITE URN contains a range, and can extract identifiers for the beginning and end objects in a range.  If the range is cited at the version level, it can extract individual version identifiers for the beginning and end objects in a range.  At either the notional object level or the version level, it can extract any extended references attached to either  beginning or end identifier.


@openex@


### Examples ###

The URN <strong concordion:set="#urn">urn:cite:examples:bifolios.1@verso-3</strong> refers to a range of objects in an ordered collection. We can determine that:

-  the URN <strong concordion:assertTrue="isRange(#urn)">is a range</strong>
-  the first object in the range has the identifier <strong concordion:assertEquals="firstObj(#urn)">1</strong>
-  the second object in the range has the identifier <strong concordion:assertEquals="secondObj(#urn)">3</strong>
- the first object has an extended reference with value <strong concordion:assertEquals="firstExtended(#urn)">verso</strong>
- the second object does not have an extended reference:  requesting it results in a <strong concordion:assertEquals="secondExtended(#urn)">null</strong> value



The URN <strong concordion:set="#urn2">urn:cite:examples:bifolios.1.v1@verso-3.v1</strong> refers by specific version to a range of objects in an ordered collection. We can determine that:


-  the URN <strong concordion:assertTrue="isRange(#urn2)">is a range</strong>
-  the first object in the range has the identifier <strong concordion:assertEquals="firstObj(#urn2)">1</strong>
-  the second object in the range has the identifier <strong concordion:assertEquals="secondObj(#urn2)">3</strong>
- the first object has an extended reference with value <strong concordion:assertEquals="firstExtended(#urn2)">verso</strong>
- the second object does not have an extended reference:  requesting it results in a <strong concordion:assertEquals="secondExtended(#urn2)">null</strong> value


@closeex@