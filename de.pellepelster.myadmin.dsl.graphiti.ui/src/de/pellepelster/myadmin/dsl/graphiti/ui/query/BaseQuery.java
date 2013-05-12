package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuery
{
	private List<Boolean> results = new ArrayList<>();

	protected BaseQuery()
	{
		super();
	}

	public List<Boolean> getResults()
	{
		return this.results;
	}

	public boolean result()
	{
		for (Boolean result : this.results)
		{
			if (!result)
			{
				return false;
			}
		}

		return true;
	}

}
