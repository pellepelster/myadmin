package de.pellepelster.myadmin.server.services.search;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.IDictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexElementQuery;
import de.pellepelster.myadmin.db.index.SearchIndexElement;
import de.pellepelster.myadmin.db.index.SearchIndexElementQuery;

public class DictionarySearchIndexFactory
{

	private final static String DICTIONARY_LABEL_SEARCH_INDEX_TYPE = "dictionaryLabel";

	private final static String DICTIONARY_LABEL_TEXT_FIELD_NAME = "labelText";

	private final static String DICTIONARY_NAME_FIELD_NAME = "dictionaryName";

	private final static String VO_CLASS_FIELD_NAME = "voClass";

	private final static String VO_ID_FIELD_NAME = "voId";

	public static ISearchIndexElementQuery createElementQuery(IDictionaryDescriptor dictionary)
	{
		Map<String, String> idFields = new HashMap<String, String>();

		idFields.put(DICTIONARY_NAME_FIELD_NAME, dictionary.getId());
		// idFields.put(DICTIONARY_LABEL_TEXT_FIELD_NAME, ));

		SearchIndexElementQuery searchIndexElementQuery = new SearchIndexElementQuery(DICTIONARY_LABEL_SEARCH_INDEX_TYPE, idFields);
		return searchIndexElementQuery;
	}

	public static ISearchIndexElement createElement(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		Map<String, String> idFields = new HashMap<String, String>();

		idFields.put(DICTIONARY_NAME_FIELD_NAME, dictionaryModel.getName());
		idFields.put(VO_CLASS_FIELD_NAME, vo.getClass().getName());
		idFields.put(VO_ID_FIELD_NAME, Long.toString(vo.getId()));

		SearchIndexElement searchIndexElement = new SearchIndexElement(DICTIONARY_LABEL_SEARCH_INDEX_TYPE, idFields, DictionaryUtil.getLabel(
				dictionaryModel.getLabelControls(), vo));
		return searchIndexElement;
	}
}
