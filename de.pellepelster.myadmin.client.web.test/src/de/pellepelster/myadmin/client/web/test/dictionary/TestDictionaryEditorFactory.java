package de.pellepelster.myadmin.client.web.test.dictionary;

import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;

public class TestDictionaryEditorFactory
{
	private DictionaryEditorVO dictionaryEditorVO;

	public TestDictionaryEditorFactory(DictionaryVO dictionaryVO)
	{
		this.dictionaryEditorVO = new DictionaryEditorVO();
		DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
		this.dictionaryEditorVO.setContainer(dictionaryContainerVO);
		dictionaryVO.setEditor(this.dictionaryEditorVO);

		DictionaryContainerVO editorRootCompositeVO = new DictionaryContainerVO();
		editorRootCompositeVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);
		editorRootCompositeVO.setName(ICompositeModel.ROOT_COMPOSITE_NAME);
		this.dictionaryEditorVO.setContainer(editorRootCompositeVO);
	}

	public TestDictionaryEditorFactory addContainer(DictionaryContainerVO dictionaryContainerVO)
	{
		this.dictionaryEditorVO.getContainer().getChildren().add(dictionaryContainerVO);
		return this;
	}

	public TestDictionaryEditorFactory addControl(DictionaryControlVO dictionaryControlVO)
	{
		this.dictionaryEditorVO.getContainer().getControls().add(dictionaryControlVO);
		return this;
	}

}
