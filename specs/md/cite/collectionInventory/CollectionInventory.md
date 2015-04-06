# CITE Collection Inventory #


A CITE Collection Inventory 


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