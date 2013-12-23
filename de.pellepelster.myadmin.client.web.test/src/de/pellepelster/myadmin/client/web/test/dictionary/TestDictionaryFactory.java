package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;

public class TestDictionaryFactory
{
	private DictionaryVO dictionaryVO;

	private TestDictionaryEditorFactory dictionaryEditorFactory;

	private TestDictionarySearchFactory dictionarySearchFactory;

	private String dictionaryName;

	private TestDictionaryFactory(String dictionaryName, String entityName)
	{
		this.dictionaryVO = new DictionaryVO();
		this.dictionaryVO.setName(dictionaryName);
		this.dictionaryVO.setEntityName(entityName);

		createSearch().createResult();
	}

	public static TestDictionaryFactory create(String dictionaryName, String entityName)
	{
		return new TestDictionaryFactory(dictionaryName, entityName);
	}

	public DictionaryModel getDictionaryModel()
	{
		return new DictionaryModel(this.dictionaryName, null);
	}

	public TestDictionaryEditorFactory createEditor()
	{
		if (this.dictionaryEditorFactory == null)
		{
			this.dictionaryEditorFactory = new TestDictionaryEditorFactory(this.dictionaryVO);
		}

		return this.dictionaryEditorFactory;
	}

	public TestDictionarySearchFactory createSearch()
	{
		if (this.dictionarySearchFactory == null)
		{
			this.dictionarySearchFactory = new TestDictionarySearchFactory(this.dictionaryVO);
		}

		return this.dictionarySearchFactory;
	}

	public TestDictionaryFactory addLabelControl(DictionaryControlVO dictionaryControlVO)
	{
		this.dictionaryVO.getLabelControls().add(dictionaryControlVO);
		return this;
	}

	public TestDictionaryFactory addControlToAll(DictionaryControlVO dictionaryControlVO)
	{
		createEditor().addControl(dictionaryControlVO);

		createSearch().addControlToAll(dictionaryControlVO);

		addLabelControl(dictionaryControlVO);

		return this;
	}
}
