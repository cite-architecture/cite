package edu.harvard.chs.cite
//import edu.harvard.chs.cite.CiteUrn
//import edu.harvard.chs.cite.Cite2Urn


/* A Class to convert CITE Urns to CITE2 urns
**/

class Cite2Converter {

	/**
	* Gets the CITE URN object as a String.
	* @param CiteUrn A cite urn
	* @returns a Cite2Urn
	*/
	Cite2Urn convert(CiteUrn c1urn)
	throws Exception {
		Cite2Urn c2urn
		try{
			String urnStr = "urn:cite2:"
			urnStr += "${c1urn.ns}:"
			urnStr += "${c1urn.collection}"
		  if (c1urn.objectVersion != null){
				urnStr += ".${c1urn.objectVersion}"
			} else if (c1urn.objectVersion_1 != null){
				urnStr += ".${c1urn.objectVersion_1}"
			} else if (c1urn.objectVersion_2 != null){
				urnStr += ".${c1urn.objectVersion_2}"
			}
			urnStr += ":"
			if(c1urn.isRange()){
				if (c1urn.objectId_1 != null){
					urnStr += "${c1urn.objectId_1}"
				}
				if (c1urn.extendedRef_1 != null){
					urnStr += "@${c1urn.extendedRef_1}"
				}
				urnStr += "-"
				if (c1urn.objectId_2 != null){
					urnStr += "${c1urn.objectId_2}"
				}
				if (c1urn.extendedRef_2 != null){
					urnStr += "@${c1urn.extendedRef_2}"
				}

			} else {
				if (c1urn.objectId != null){
					urnStr += "${c1urn.objectId}"
				}
				if (c1urn.extendedRef != null){
					urnStr += "@${c1urn.extendedRef}"
				}
			}

			c2urn = new Cite2Urn(urnStr)
			return c2urn
		} catch(Exception e){
			throw new Exception("Cite2Converter: conversion error for ${c1urn}: ${e}")
		}
	}

}
