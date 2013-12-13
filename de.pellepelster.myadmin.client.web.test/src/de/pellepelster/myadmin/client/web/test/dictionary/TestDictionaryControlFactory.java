package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_BASETYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryDatatypeVO;

public class TestDictionaryControlFactory
{

	public static DictionaryControlVO createTextControl(IAttributeDescriptor<String> attributeDescriptor)
	{
		return createTextControl(attributeDescriptor.getAttributeName(), attributeDescriptor.getAttributeName());
	}

	public static DictionaryControlVO createHierarchicalControl(IAttributeDescriptor<IHierarchicalVO> attributeDescriptor)
	{
		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(attributeDescriptor.getAttributeName());
		dictionaryControlVO.setColumnLabel(attributeDescriptor.getAttributeName());
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(attributeDescriptor.getAttributeName());
		dictionaryControlVO.setAttributePath(attributeDescriptor.getAttributeName());

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.HIERARCHICAL);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	public static DictionaryControlVO createTextControl(String label, String attributePath)
	{
		return createTextControl(label, attributePath, false);
	}

	public static DictionaryControlVO createTextControl(String label, String attributePath, boolean mandatory)
	{
		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(mandatory);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.TEXT);
		dictionaryDatatypeVO.setMaxLength(16);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}
}
