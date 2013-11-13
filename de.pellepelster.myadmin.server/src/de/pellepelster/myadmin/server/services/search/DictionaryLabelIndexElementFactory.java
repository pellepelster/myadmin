package de.pellepelster.myadmin.server.services.search;

import java.util.Map;
import java.util.UUID;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexElementQuery;
import de.pellepelster.myadmin.db.index.SearchIndexElement;
import de.pellepelster.myadmin.db.index.SearchIndexElementQuery;

@Component
public class DictionaryLabelIndexElementFactory implements ISearchIndexElementFactory
{

	public static final String SEARCH_INDEX_ELEMENT_TYPE = "dictionaryLabel";

	public static final String DICTIONARY_NAME_FIELD_NAME = "dictionaryName";

	public static final String VO_CLASS_NAME_FIELD_NAME = "voClassName";

	public static final String VO_ID_FIELD_NAME = "voId";

	public static ISearchIndexElementQuery createElementQuery(String dictionaryName, IBaseVO vo)
	{
		SearchIndexElementQuery indexElementQuery = new SearchIndexElementQuery(SEARCH_INDEX_ELEMENT_TYPE);
		indexElementQuery.getFields().put(DICTIONARY_NAME_FIELD_NAME, dictionaryName);

		if (vo != null)
		{
			indexElementQuery.getFields().put(VO_CLASS_NAME_FIELD_NAME, vo.getClass().getName());
			indexElementQuery.getFields().put(VO_ID_FIELD_NAME, Long.toString(vo.getId()));
		}

		return indexElementQuery;
	}

	public static ISearchIndexElementQuery createElementQuery(DictionaryDescriptor dictionaryDescriptor)
	{
		return createElementQuery(dictionaryDescriptor.getId(), null);
	}

	public static ISearchIndexElementQuery createElementQuery(IDictionaryModel dictionaryModel)
	{
		return createElementQuery(dictionaryModel.getName(), null);
	}

	public static ISearchIndexElementQuery createElementQuery(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		return createElementQuery(dictionaryModel.getName(), vo);
	}

	public static ISearchIndexElement createElement(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		SearchIndexElement indexElement = new SearchIndexElement(SEARCH_INDEX_ELEMENT_TYPE);

		indexElement.setText(DictionaryUtil.getLabel(dictionaryModel.getLabelControls(), vo));

		indexElement.getFields().put(DICTIONARY_NAME_FIELD_NAME, dictionaryModel.getName());
		indexElement.getFields().put(VO_CLASS_NAME_FIELD_NAME, vo.getClass().getName());
		indexElement.getFields().put(VO_ID_FIELD_NAME, Long.toString(vo.getId()));

		return indexElement;
	}

	@Override
	public String getType()
	{
		return SEARCH_INDEX_ELEMENT_TYPE;
	}

	@Override
	public ISearchIndexElement getSearchIndexElement(SolrDocument solrDocument)
	{
		SearchIndexElement indexElement = new SearchIndexElement(SEARCH_INDEX_ELEMENT_TYPE);

		indexElement.setText(SolrUtils.getDynamicStringField(solrDocument, ISearchIndexElementFactory.SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME));

		SolrUtils.setDynamicStringField(indexElement, solrDocument, DICTIONARY_NAME_FIELD_NAME);
		SolrUtils.setDynamicStringField(indexElement, solrDocument, VO_CLASS_NAME_FIELD_NAME);
		SolrUtils.setDynamicStringField(indexElement, solrDocument, VO_ID_FIELD_NAME);

		return indexElement;
	}

	@Override
	public SolrInputDocument getSolrDocument(ISearchIndexElement indexElement)
	{
		SolrInputDocument document = new SolrInputDocument();

		document.setField(SEARCH_INDEX_ID_FIELD_NAME, UUID.randomUUID().toString());

		document.setField(SolrUtils.getDynamicStringFieldName(SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME), indexElement.getType());
		document.setField(SolrUtils.getDynamicStringFieldName(SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME), indexElement.getText());

		for (Map.Entry<String, String> idField : indexElement.getFields().entrySet())
		{
			document.addField(SolrUtils.getDynamicStringFieldName(idField.getKey()), idField.getValue());
		}

		return document;
	}

}
