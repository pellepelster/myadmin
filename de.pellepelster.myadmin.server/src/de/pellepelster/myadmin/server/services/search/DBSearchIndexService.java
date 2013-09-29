package de.pellepelster.myadmin.server.services.search;

import org.apache.log4j.Logger;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexService;

public class DBSearchIndexService implements ISearchIndexService
{
	private final static Logger LOG = Logger.getLogger(DBSearchIndexService.class);

	@Override
	public void add(ISearchIndexElement indexElement)
	{
		LOG.debug(String.format("indexing '%s'", indexElement.toString()));

		try
		{

			// for (Map.Entry<String, String> idField :
			// indexElement.getIdFields().entrySet())
			// {
			// document.addField(idField.getKey(), idField.getValue());
			// }

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(ISearchIndexElement indexElement)
	{
	}

	@Override
	public void update(ISearchIndexElement baseVO)
	{
	}

	@Override
	public void deleteAll(String elementType)
	{
	}

	@Override
	public long getCount(String elementType)
	{
		return 0;
	}

}
