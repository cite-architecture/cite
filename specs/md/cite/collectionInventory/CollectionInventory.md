I# CITE Collection Inventory #


## CITE Collections ##

A minimum valid CITE Collection is made up:

- a CITE URN identifying the collection
- a CiteProperty object for the collection's canonical ID property
- a CiteProperty object for the collection's labelling property
- a URI uniquely identifying the abbreviated CITE namespace named in the Collection's URN


@openex@

### Examples ###

We can initialize a valid CITE Collection as follows.

First, using these properties: 

- name = <strong concordion:set="#name2">Image</strong>
- type =  <strong concordion:set="#type2">citeurn</strong>
- label =  <strong concordion:set="#label2">Image URN</strong>

we can construct 
<strong concordion:assertTrue="isValidProp(#name2,#type2,#label2)">a valid CiteProperty for the Collection's  canonical ID property</strong>.

Then, using these properties, we can construct a valid CiteProperty for the Collection's label property:


- name = <strong concordion:set="#name3">label</strong>
- type =  <strong concordion:set="#type3">string</strong>
- label =  <strong concordion:set="#label3">Caption</strong>

we can construct <strong concordion:assertTrue="isValidProp(#name2,#type2,#label2)">a valid CiteProperty for the Collection's  label property</strong>.

If we give this collection the URN <strong concordion:set="#imgurn">urn:cite:hmt:vaimg</strong>,  with the `hmt` namespace abbreviation expanded to <strong concordion:set="#fulluri">http://homermultitext.org/citens</strong>, and source named <strong concordion:set="#srcname">images.csv</strong> we can create  <strong  concordion:assertTrue="isValidCollection(#name3,#type3,#label3,#name3,#type3,#label3,#imgurn,#fulluri,#srcname)">a valid CITE Collection object</strong>.


@closeex@




## CITE Properties ##

Every CITEProperty object is defined by three strings:  a *name* that is unique within the Collection a belongs to, a *type*,  and a human-readable display *label*.  The value of *type* must be one of  `string`, `number`, `boolean`, or `markdown`.  The `cite` library can construct CITEProperty objects from ordered triples of strings in the sequence name, type and label.
@openex@

### Examples ###

Given these definitions:

- name = <strong concordion:set="#name1">rv</strong>
- type =  <strong concordion:set="#type1">string</strong>
- label =  <strong concordion:set="#label1">Recto or Verso</strong>

we can <strong concordion:assertTrue="isValidProp(#name1,#type1,#label1)">construct a valid CiteProperty</strong>.


If instead we use the string <strong concordion:set="#badtype">text_data</strong> for the type value, the library <strong concordion:assertFalse="isValidProp(#name1,#badtype,#label1)">throws an Exception</strong>, because **text_data** is not a valid value for a CITEProperty type.


@closeex@



For `string` properties, it is also possible to define a set of controlled vocabulary items.


@openex@

### Examples ###


If we use these values:

- name = <strong concordion:set="#name1">rv</strong>
- label =  <strong concordion:set="#label1">Recto or Verso</strong>

and further define a list of allowed values [<strong concordion:set="#vocab">recto,verso</strong>], we can <strong concordion:assertTrue="isValidControlledProp(#name1,#label1,#vocab)">construct a CITEProperty with controlled vocabulary</strong>.



@closeex@


## Extensions ##

A valid CITE Extension includes:

- an abbreviation identifying the extension
- a URI expanding the extension's abbreviation
- an RDF type, expressed with abbreviated RDF namespace
- a URI expanding the RDF namespace abbreviation
- a boolean value indicating whether this extension defines an extended citation notation
- a set of one or more names of requests


@openex@

If we define an extension with these values:

- abbreviation <strong concordion:set="#extname">image</strong>
- URI for extension <strong concordion:set="#exturi">http://cite-architecture.github.io/extensions/image</strong>
- RDF type <strong concordion:set="#rdftype">cite:Image</strong>
- URI for the `cite` RDF namespace <strong concordion:set="#rdfuri">http://cite-architecture.github.io/extensions/image</strong>
- extended notation <strong concordion:set="#extendcite">true</strong>
- a set of request names <strong concordion:set="#reqlist">GetBinaryImage,GetCaption,GetRights,GetIIPMooviewer</strong>

then we can <strong concordion:assertTrue="isValidExt(#extname, #exturi, #rdftype, #rdfuri, #extendcite, #reqlist)">construct a valid CITE Extension</strong> object.

@closeex@