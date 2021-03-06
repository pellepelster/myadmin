package de.pellepelster.myadmin.client.base.modules.dictionary.model.search;

import java.util.Collections;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;

public class ResultModel extends BaseTableModel<Object> implements IResultModel
{
	private static final long serialVersionUID = 7452528927479882166L;

	private int maxResults = DEFAULT_MAX_RESULTS;

	public ResultModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public List<IBaseContainerModel> getChildren()
	{
		return Collections.emptyList();
	}

	@Override
	public int getMaxResults()
	{
		return this.maxResults;
	}

	@Override
	public String getAttributePath()
	{
		return null;
	}

}
