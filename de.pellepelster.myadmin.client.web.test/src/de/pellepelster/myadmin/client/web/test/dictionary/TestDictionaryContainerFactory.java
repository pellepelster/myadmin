package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

public class TestDictionaryContainerFactory
{

	public static DictionaryContainerVO createAssigmentTable(IAttributeDescriptor<?> attributeDescriptor, DictionaryControlVO dictionaryControlVO)
	{
		DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
		dictionaryContainerVO.setType(DICTIONARY_CONTAINER_TYPE.ASSIGNMENT_TABLE);
		dictionaryContainerVO.setAttributePath(attributeDescriptor.getAttributeName());
		dictionaryContainerVO.getControls().add(dictionaryControlVO);

		return dictionaryContainerVO;
	}
}
