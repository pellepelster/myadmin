package de.pellepelster.myadmin.server.services.search;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexElementQuery;
import de.pellepelster.myadmin.db.index.SearchIndexElement;
import de.pellepelster.myadmin.db.index.SearchIndexElementQuery;

public class DictionaryLabelIndexElementFactory
{
	private static final String DICTIONARY_NAME_FIELD_NAME = "dictionaryName";

	private static final String SEARCH_INDEX_ELEMENT_TYPE = "dictionaryLabel";

	private DictionaryLabelIndexElementFactory()
	{
	}

	public static Map<String, String> getIdFields(String dictionaryName)
	{
		Map<String, String> idFields = new HashMap<String, String>();
		idFields.put(DICTIONARY_NAME_FIELD_NAME, dictionaryName);

		return idFields;
	}

	public static ISearchIndexElement createIndexElement(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		return new SearchIndexElement(SEARCH_INDEX_ELEMENT_TYPE, getIdFields(dictionaryModel.getName()), DictionaryUtil.getLabel(
				dictionaryModel.getLabelControls(), vo));
	}

	public static ISearchIndexElementQuery createIndexElementQuery(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		return new SearchIndexElementQuery(SEARCH_INDEX_ELEMENT_TYPE, getIdFields(dictionaryModel.getName()));
	}

}
