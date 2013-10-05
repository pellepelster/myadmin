package de.pellepelster.myadmin.server.services.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexElementQuery;
import de.pellepelster.myadmin.db.index.ISearchIndexService;

public class SolrSearchIndexService implements ISearchIndexService
{
	private Map<String, ISearchIndexElementFactory> searchIndexElementFactories = new HashMap<String, ISearchIndexElementFactory>();

	private final static Logger LOG = Logger.getLogger(SolrSearchIndexService.class);

	private SolrServer server = new HttpSolrServer("http://localhost:8380/solr/");

	@Override
	public void add(ISearchIndexElement indexElement)
	{
		LOG.debug(String.format("indexing '%s'", indexElement.toString()));
		try
		{
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

			SolrInputDocument document = getSearchIndexElementFactory(indexElement.getType()).getSolrDocument(indexElement);

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
	public void update(ISearchIndexElement baseVO)
	{
		// TODO Auto-generated method stub

	}

	private String getQuery(ISearchIndexElementQuery elementQuery)
	{

		StringBuilder sb = new StringBuilder();

		SolrUtils.addDynamicStringField(sb, ISearchIndexElementFactory.SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, elementQuery.getType());

		for (Map.Entry<String, String> idFieldEntry : elementQuery.getFields().entrySet())
		{
			sb.append(" AND ");
			SolrUtils.addDynamicStringField(sb, idFieldEntry.getKey(), idFieldEntry.getValue());
		}

		if (!StringUtils.isEmpty(elementQuery.getText()))
		{
			sb.append(" AND ");
			SolrUtils.addDynamicStringField(sb, ISearchIndexElementFactory.SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME, "*" + elementQuery.getText() + "*");
		}

		return sb.toString();
	}

	@Override
	public void delete(ISearchIndexElementQuery elementQuery)
	{
		deleteAll(elementQuery);
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

	@Override
	public List<ISearchIndexElement> search(ISearchIndexElementQuery elementQuery)
	{
		List<ISearchIndexElement> results = new ArrayList<ISearchIndexElement>();

		String searchQuery = getQuery(elementQuery);
		LOG.debug(String.format("searchelements with query '%s'", searchQuery));

		QueryResponse rsp = null;

		try
		{

			SolrQuery query = new SolrQuery(searchQuery);
			rsp = this.server.query(query);
		}

		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		for (SolrDocument solrDocument : rsp.getResults())
		{
			ISearchIndexElement searchIndexElement = getSearchIndexElementFactory(solrDocument).getSearchIndexElement(solrDocument);
			results.add(searchIndexElement);
		}

		return results;
	}

	private ISearchIndexElementFactory getSearchIndexElementFactory(SolrDocument solrDocument)
	{
		return getSearchIndexElementFactory(Objects.toString(solrDocument.getFieldValue(ISearchIndexElementFactory.SEARCH_INDEX_ELEMENT_TYPE_FIELD_NAME)));
	}

	private ISearchIndexElementFactory getSearchIndexElementFactory(String elementType)
	{
		ISearchIndexElementFactory searchIndexElementFactory = this.searchIndexElementFactories.get(elementType);

		if (searchIndexElementFactory != null)
		{
			return searchIndexElementFactory;
		}
		else
		{
			throw new RuntimeException(String.format("unknown element type '%s'", elementType));
		}
	}

	@Autowired
	public void setSearchIndexElementFactories(List<ISearchIndexElementFactory> searchIndexElementFactories)
	{
		for (ISearchIndexElementFactory searchIndexElementFactory : searchIndexElementFactories)
		{
			this.searchIndexElementFactories.put(searchIndexElementFactory.getType(), searchIndexElementFactory);
		}
	}

}
