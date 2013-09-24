package de.pellepelster.myadmin.server.services.search;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexService;

public class SearchIndexService implements ISearchIndexService
{
	private final static Logger LOG = Logger.getLogger(SearchIndexService.class);

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
		try
		{
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

			SolrInputDocument document = new SolrInputDocument();
			document.setField(SEARCH_INDEX_ID_FIELD_NAME, indexElement.getId());
			document.setField(SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, indexElement.getType());

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
