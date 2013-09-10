package de.pellepelster.myadmin.client.web.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.DictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryResultVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;

public class TestDictionaryFactory
{
	private DictionaryVO dictionaryVO;

	private TestDictionaryFactory(String dictionaryName, String entityName)
	{
		this.dictionaryVO = new DictionaryVO();
		this.dictionaryVO.setName(dictionaryName);
		this.dictionaryVO.setEntityName(entityName);
	}

	public static TestDictionaryFactory create(String dictionaryName, String entityName)
	{
		return new TestDictionaryFactory(dictionaryName, entityName);
	}

	public DictionaryModel getDictionaryModel()
	{
		return new DictionaryModel(this.dictionaryVO);
	}

	public TestDictionaryFactory createEditor()
	{
		DictionaryEditorVO dictionaryEditorVO = new DictionaryEditorVO();
		DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
		dictionaryEditorVO.setContainer(dictionaryContainerVO);
		this.dictionaryVO.setEditor(dictionaryEditorVO);

		return this;
	}

	public TestDictionaryFactory createSearch()
	{
		DictionarySearchVO dictionarySearchVO = new DictionarySearchVO();
		this.dictionaryVO.setSearch(dictionarySearchVO);

		return this;
	}

	public TestDictionaryFactory createFilter()
	{
		DictionaryFilterVO dictionaryFilterVO = new DictionaryFilterVO();
		this.dictionaryVO.getSearch().setFilter(Arrays.asList(new DictionaryFilterVO[] { dictionaryFilterVO }));

		return this;
	}

	public TestDictionaryFactory createResult()
	{
		DictionaryResultVO dictionaryResultVO = new DictionaryResultVO();
		this.dictionaryVO.getSearch().setResult(dictionaryResultVO);

		return this;
	}

	public TestDictionaryFactory addControlToAll(DictionaryControlVO dictionaryControlVO)
	{
		createEditor();
		createSearch();
		createFilter();
		createResult();

		List<DictionaryControlVO> dictionaryControlVOs = new ArrayList<DictionaryControlVO>();
		dictionaryControlVOs.add(dictionaryControlVO);

		DictionaryContainerVO controlsContainerVO = new DictionaryContainerVO();
		controlsContainerVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);
		controlsContainerVO.getControls().addAll(dictionaryControlVOs);

		this.dictionaryVO.setLabelControls(dictionaryControlVOs);
		this.dictionaryVO.getEditor().setContainer(controlsContainerVO);

		for (DictionaryFilterVO dictionaryFilterVO : this.dictionaryVO.getSearch().getFilter())
		{
			dictionaryFilterVO.setContainer(controlsContainerVO);
		}

		this.dictionaryVO.getSearch().getResult().setControls(dictionaryControlVOs);

		return this;
	}
}
