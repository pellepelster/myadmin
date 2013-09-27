package de.pellepelster.myadmin.server.services.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.index.ISearchIndexElement;

public class DictionaryIndexElement implements ISearchIndexElement
{
	public static final String SEARCH_INDEX_TYPE = "dictionary";

	public final String id;

	public final String type;

	private Map<String, String> idFields = new HashMap<String, String>();

	public DictionaryIndexElement(String dictionaryName, List<String> list, IBaseVO vo)
	{
		this.idFields.put(SEARCH_INDEX_TYPE, dictionaryName);
		this.idFields.put(IBaseVO.FIELD_ID.getAttributeName(), Long.toString(vo.getId()));

		this.id = String.format("%s#%d", vo.getClass().getName(), vo.getId());
		this.type = vo.getClass().getName();
	}

	@Override
	public Map<String, String> getIdFields()
	{
		return this.idFields;
	}

	@Override
	public String getType()
	{
		return this.type;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public String toString()
	{

		return String.format("%s:", getId());
	}

}
