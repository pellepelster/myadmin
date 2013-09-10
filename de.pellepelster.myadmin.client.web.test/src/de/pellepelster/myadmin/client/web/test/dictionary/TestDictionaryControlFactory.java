package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_BASETYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryDatatypeVO;

public class TestDictionaryControlFactory
{

	public static DictionaryControlVO createTextControl(IAttributeDescriptor<String> attributeDescriptor)
	{
		return createTextControl(attributeDescriptor.getAttributeName(), attributeDescriptor.getAttributeName());
	}

	public static DictionaryControlVO createTextControl(String label, String attributePath)
	{
		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.TEXT);
		dictionaryDatatypeVO.setMaxLength(16);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}
}
