package de.pellepelster.myadmin.server.services.search;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.db.index.ISearchIndexElement;

public class DictionaryLabelIndexElement implements ISearchIndexElement
{
	private static final String DICTIONARY_NAME_FIELD_NAME = "dictionaryName";

	private Map<String, String> idFields = new HashMap<String, String>();

	private final String text;

	public DictionaryLabelIndexElement(String dictionaryName, String text)
	{
		// this.id = UUID.randomUUID().toString();
		this.text = text;

		this.idFields.put(DICTIONARY_NAME_FIELD_NAME, dictionaryName);
	}

	@Override
	public String getType()
	{
		return getClass().getName();
	}

	@Override
	public String getText()
	{
		return this.text;
	}

	public static ISearchIndexElement create(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		return new DictionaryLabelIndexElement(dictionaryModel.getName(), DictionaryUtil.getLabel(dictionaryModel.getLabelControls(), vo));
	}

	@Override
	public Map<String, String> getIdFields()
	{
		return this.idFields;
	}

}
