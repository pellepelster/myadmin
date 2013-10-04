package de.pellepelster.myadmin.server.services.search;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;

public interface ISearchIndexElementFactory
{
	static final String SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME = "text";

	static final String SEARCH_INDEX_ID_FIELD_NAME = "id";

	static final String DYNAMIC_STRING_FIELD_POSTFIX = "_s";

	static final String ID_DELIMITER = "#";

	static final String SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME = "type";

	String getType();

	ISearchIndexElement getSearchIndexElement(SolrDocument solrDocument);

	SolrInputDocument getSolrDocument(ISearchIndexElement searchIndexElement);

}
