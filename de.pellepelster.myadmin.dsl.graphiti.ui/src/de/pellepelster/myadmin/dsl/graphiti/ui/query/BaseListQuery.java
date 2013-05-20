package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import java.util.Collection;

public class BaseListQuery<T> extends BaseQuery
{
	private Collection<T> list;

	public BaseListQuery(Collection<T> list)
	{
		super();
		this.list = list;
	}

	public Collection<T> getList()
	{
		return this.list;
	}

	public void setList(Collection<T> list)
	{
		this.list = list;
	}

}
