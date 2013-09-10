package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryResultVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;

public class TestDictionaryResultFactory
{
	private DictionaryResultVO dictionaryResultVO;

	public TestDictionaryResultFactory(DictionarySearchVO dictionarySearchVO)
	{
		this.dictionaryResultVO = new DictionaryResultVO();
		dictionarySearchVO.setResult(this.dictionaryResultVO);
	}

	public TestDictionaryResultFactory addControl(DictionaryControlVO dictionaryControlVO)
	{
		this.dictionaryResultVO.getControls().add(dictionaryControlVO);
		return this;
	}
}
