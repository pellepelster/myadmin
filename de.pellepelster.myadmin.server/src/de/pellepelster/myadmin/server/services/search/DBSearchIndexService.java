package de.pellepelster.myadmin.server.services.search;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.db.index.ISearchIndexElement;
import de.pellepelster.myadmin.db.index.ISearchIndexElementQuery;
import de.pellepelster.myadmin.db.index.ISearchIndexService;

public class DBSearchIndexService implements ISearchIndexService
{

	@Autowired
	private IBaseVODAO baseVODAO;

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
		// do nothing
		;
	}

	@Override
	public void update(ISearchIndexElement baseVO)
	{
		// do nothing
		;
	}

	@Override
	public void deleteAll(ISearchIndexElementQuery elementQuery)
	{
		// do nothing
		;
	}

	@Override
	public long getCount(ISearchIndexElementQuery elementQuery)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
