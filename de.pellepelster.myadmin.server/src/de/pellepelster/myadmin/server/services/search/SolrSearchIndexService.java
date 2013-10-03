package de.pellepelster.myadmin.server.services.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexElementQuery;
import de.pellepelster.myadmin.db.index.ISearchIndexService;

public class SolrSearchIndexService implements ISearchIndexService
{

	static final String SEARCH_INDEX_ID_FIELD_NAME = "id";

	static final String DYNAMIC_STRING_FIELD_POSTFIX = "_s";

	static final String ID_DELIMITER = "#";

	static final String SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME = "type";

	static final String SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME = "text";

	private final static Logger LOG = Logger.getLogger(SolrSearchIndexService.class);

	private SolrServer server = new HttpSolrServer("http://localhost:8380/solr/");

	// private String getLabelText(IBaseVO baseVO)
	// {
	// if (hasIndexAttributes(baseVO))
	// {
	// // String labelText
	// }
	//
	// return null;
	// }
	//
	// private boolean hasIndexAttributes(IBaseVO baseVO)
	// {
	// return
	// this.dictionaryLabelIndexAttributes.containsKey(baseVO.getClass().getName());
	// }

	@Override
	public void add(ISearchIndexElement indexElement)
	{
		LOG.debug(String.format("indexing '%s'", indexElement.toString()));
		try
		{
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

			SolrInputDocument document = new SolrInputDocument();

			document.setField(getDynamicStringFieldName(SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME), indexElement.getType());
			document.setField(getDynamicStringFieldName(SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME), indexElement.getText());
			document.setField(SEARCH_INDEX_ID_FIELD_NAME, UUID.randomUUID().toString());

			for (Map.Entry<String, String> idField : indexElement.getIdFields().entrySet())
			{
				document.addField(getDynamicStringFieldName(idField.getKey()), idField.getValue());
			}

			docs.add(document);

			this.server.add(docs);
			this.server.commit();

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(ISearchIndexElement indexElement)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ISearchIndexElement baseVO)
	{
		// TODO Auto-generated method stub

	}

	private String getDynamicStringFieldName(String fieldName)
	{
		return fieldName + DYNAMIC_STRING_FIELD_POSTFIX;
	}

	private void addDynamicStringField(StringBuilder sb, String fieldName, String fieldValue)
	{
		sb.append(String.format("%s:%s", getDynamicStringFieldName(fieldName), fieldValue));
	}

	private String getQuery(ISearchIndexElementQuery elementQuery)
	{

		StringBuilder sb = new StringBuilder();

		addDynamicStringField(sb, SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, elementQuery.getType());

		sb.append(" AND ");

		for (Map.Entry<String, String> idFieldEntry : elementQuery.getIdFields().entrySet())
		{
			addDynamicStringField(sb, idFieldEntry.getKey(), idFieldEntry.getValue());
		}

		return sb.toString();
	}

	@Override
	public void deleteAll(ISearchIndexElementQuery elementQuery)
	{

		try
		{

			String deleteQuery = getQuery(elementQuery);
			LOG.debug(String.format("deleting all search elements for query '%s'", deleteQuery));

			this.server.deleteByQuery(deleteQuery);
			this.server.commit();

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getCount(ISearchIndexElementQuery elementQuery)
	{
		try
		{
			String countQuery = getQuery(elementQuery);
			LOG.debug(String.format("counting all search elements for query '%s'", countQuery));

			SolrQuery query = new SolrQuery(countQuery);
			QueryResponse rsp = this.server.query(query);

			return rsp.getResults().getNumFound();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
