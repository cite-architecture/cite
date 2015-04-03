# Working with parts of a CITE Object URN #


## Top-level components ##


The `cite` library can extract the required top-level components of a CITE URN.

@openex@

### Examples ###

The top level components of the URN
<strong concordion:set="#urn">urn:cite:hmt:msA.12r</strong> are:

- namespace:  <strong concordion:assertEquals="getNs(#urn)">hmt</strong>
- object component:  <strong concordion:assertEquals="getObjComponent(#urn)">msA.12r</strong>

@closeex@


## The object component

The `cite`  library can determine what  optional parts of the object component are present, and can extract them from the object component if they exist.

@openex@

### Examples ###

The URN
<strong concordion:set="#urn">urn:cite:hmt:msA.12r</strong> :


- belongs to the collection <strong concordion:assertEquals="getCollection(#urn)">msA</strong>
- <strong concordion:assertFalse="hasVersion(#urn)">does not have a version identifier</strong>
- <strong concordion:assertTrue="hasObjId(#urn)">has an object identifer</strong> with the value <strong concordion:assertEquals="getObjId(#urn)">12r</strong>
- <strong concordion:assertFalse="hasVersion(#urn)">does not have a version identifier</strong>
- <strong concordion:assertFalse="hasExtendedRef(#urn)">does not include a type-specific extended reference</strong>


The URN <strong concordion:set="#img">urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225</strong>:



- belongs to the collection <strong concordion:assertEquals="getCollection(#img)">vaimg</strong>
- <strong concordion:assertTrue="hasObjId(#img)">has an object identifer</strong> with the value <strong concordion:assertEquals="getObjId(#img)">VA012RN_0013</strong>
- <strong concordion:assertTrue="hasVersion(#img)">has a version identifier</strong> with the value <strong concordion:assertEquals="getVersion(#img)">v1</strong>
- <strong concordion:assertTrue="hasExtendedRef(#img)">has a type-specific extended reference</strong> with the value <strong concordion:assertEquals="getExtendedRef(#img)">0.1532,0.1021,0.4014,0.0225</strong>

@closeex@


## Manipulating CITE Object URNs ##

Because every CITE URN has an object component minimally composed of a collection identifier, we can reduce any CITE URN to a URN for its collection.

@openex@

### Examples ###

The URN
<strong concordion:set="#urn">urn:cite:hmt:msA.12r</strong> belongs to the collection identified by the URN <strong concordion:assertEquals="reduceToColl(#urn)">urn:cite:hmt:msA</strong>.


The URN <strong concordion:set="#img">urn:cite:hmt:vaimg.VA012RN_0013.v1@0.1532,0.1021,0.4014,0.0225</strong>  belongs to the collection identified by the URN  <strong concordion:assertEquals="reduceToColl(#img)">urn:cite:hmt:vaimg</strong>.

@closeex@