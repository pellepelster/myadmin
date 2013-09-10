package de.pellepelster.myadmin.client.web.test.dictionary;

import java.util.Arrays;

import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;

public class TestDictionaryFilterFactory
{
	private DictionaryFilterVO dictionaryFilterVO;

	public TestDictionaryFilterFactory(DictionarySearchVO dictionarySearchVO)
	{
		this.dictionaryFilterVO = new DictionaryFilterVO();

		DictionaryContainerVO filterRootCompositeVO = new DictionaryContainerVO();
		filterRootCompositeVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);
		this.dictionaryFilterVO.setContainer(filterRootCompositeVO);

		dictionarySearchVO.setFilter(Arrays.asList(new DictionaryFilterVO[] { this.dictionaryFilterVO }));
	}

	public TestDictionaryFilterFactory addControl(DictionaryControlVO dictionaryControlVO)
	{
		this.dictionaryFilterVO.getContainer().getControls().add(dictionaryControlVO);
		return this;
	}
}
