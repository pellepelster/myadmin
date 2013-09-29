package de.pellepelster.myadmin.server.services.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexService;

public class SolrSearchIndexService implements ISearchIndexService
{
	static final String SEARCH_INDEX_ID_FIELD_NAME = "id";

	static final String DYNAMIC_STRING_FIELD_POSTFIX = "_s";

	static final String ID_DELIMITER = "#";

	static final String SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME = "type" + DYNAMIC_STRING_FIELD_POSTFIX;

	static final String SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME = "text" + DYNAMIC_STRING_FIELD_POSTFIX;

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

			document.setField(SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, indexElement.getType());
			document.setField(SEARCH_INDEX_ELEMENT_TEXT_FIELD_NAME, indexElement.getText());

			StringBuilder id = new StringBuilder();

			for (Map.Entry<String, String> idFieldEntry : indexElement.getIdFields().entrySet())
			{
				if (id.length() > 0)
				{
					id.append(ID_DELIMITER);
				}
				id.append(idFieldEntry.getValue());

				document.setField(idFieldEntry.getKey() + DYNAMIC_STRING_FIELD_POSTFIX, idFieldEntry.getValue());
			}

			document.setField(SEARCH_INDEX_ID_FIELD_NAME, id.toString());

			// for (Map.Entry<String, String> idField :
			// indexElement.getIdFields().entrySet())
			// {
			// document.addField(idField.getKey(), idField.getValue());
			// }

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

	@Override
	public void deleteAll(String elementType)
	{
		try
		{
			String deleteQuery = String.format("%s:%s", SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, elementType);
			this.server.deleteByQuery(deleteQuery);
			this.server.commit();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getCount(String elementType)
	{
		try
		{
			SolrQuery query = new SolrQuery();
			query.setQuery(String.format("%s:%s", SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, elementType));
			QueryResponse rsp = this.server.query(query);

			return rsp.getResults().getNumFound();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
