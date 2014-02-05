package de.pellepelster.myadmin.server.services.search;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import de.pellepelster.myadmin.db.index.IDictionarySearchElement;
import de.pellepelster.myadmin.db.index.ISearchElementQuery;
import de.pellepelster.myadmin.db.index.ISearchService;

public class ElasticSearchService implements ISearchService
{
	private final static Logger LOG = Logger.getLogger(ElasticSearchService.class);

	private final Client client;

	private static final String INDEX_NAME = "myadmin-dictionary";

	public ElasticSearchService()
	{
		super();

		Node node = nodeBuilder().node();
		this.client = node.client();
	}

	public ElasticSearchService(String hostname, int port)
	{
		super();
		this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(hostname, port));
	}

	@Override
	public void add(IDictionarySearchElement element)
	{
		XContentBuilder contentBuilder = null;
		try
		{
			contentBuilder = jsonBuilder().startObject();

			for (Map.Entry<String, Object> fieldEntry : element.getFields().entrySet())
			{
				contentBuilder.field(fieldEntry.getKey(), fieldEntry.getValue());
			}

			contentBuilder.endObject();

			IndexResponse response = this.client.prepareIndex(INDEX_NAME, element.getType(), element.getId()).setSource(contentBuilder).execute().actionGet();
		}
		catch (Exception e)
		{
			LOG.error(String.format("error indexing '%s'", contentBuilder), e);
		}
	}

	@Override
	public void delete(ISearchElementQuery elementQuery)
	{
	}

	@Override
	public void update(IDictionarySearchElement element)
	{
	}

	@Override
	public void deleteAll(ISearchElementQuery elementQuery)
	{
	}

	@Override
	public long getCount(ISearchElementQuery elementQuery)
	{
		return 0;
	}

	@Override
	public List<IDictionarySearchElement> search(String query)
	{
		SearchResponse response = this.client.prepareSearch(INDEX_NAME).setQuery(QueryBuilders.fieldQuery("_all", query)).execute().actionGet();

		Iterable<IDictionarySearchElement> result = Iterables.transform(response.getHits(), new Function<SearchHit, IDictionarySearchElement>()
		{
			@Override
			public IDictionarySearchElement apply(SearchHit searchHit)
			{
				return DictionarySearchElementFactory.createElement(searchHit);
			}
		});

		return Lists.newArrayList(result);
	}
}
