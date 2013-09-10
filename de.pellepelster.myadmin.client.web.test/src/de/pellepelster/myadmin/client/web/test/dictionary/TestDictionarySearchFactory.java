package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;

public class TestDictionarySearchFactory
{
	private DictionarySearchVO dictionarySearchVO;

	private TestDictionaryFilterFactory dictionaryFilterFactory;

	private TestDictionaryResultFactory dictionaryResultFactory;

	public TestDictionarySearchFactory(DictionaryVO dictionaryVO)
	{
		this.dictionarySearchVO = new DictionarySearchVO();
		dictionaryVO.setSearch(this.dictionarySearchVO);
	}

	public TestDictionaryResultFactory createResult()
	{
		if (this.dictionaryResultFactory == null)
		{
			this.dictionaryResultFactory = new TestDictionaryResultFactory(this.dictionarySearchVO);
		}

		return this.dictionaryResultFactory;
	}

	public TestDictionarySearchFactory addControlToAll(DictionaryControlVO dictionaryControlVO)
	{
		createFilter().addControl(dictionaryControlVO);
		createResult().addControl(dictionaryControlVO);
		return this;
	}

	public TestDictionaryFilterFactory createFilter()
	{
		if (this.dictionaryFilterFactory == null)
		{
			this.dictionaryFilterFactory = new TestDictionaryFilterFactory(this.dictionarySearchVO);
		}

		return this.dictionaryFilterFactory;
	}

}
