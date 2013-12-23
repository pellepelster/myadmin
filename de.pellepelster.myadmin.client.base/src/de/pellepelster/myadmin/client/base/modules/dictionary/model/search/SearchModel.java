package de.pellepelster.myadmin.client.base.modules.dictionary.model.search;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class SearchModel extends BaseModel<Object> implements ISearchModel
{
	private List<IFilterModel> filters = new ArrayList<IFilterModel>();

	private static final long serialVersionUID = 7452528927479882166L;

	private IResultModel resultModel;

	private String label;

	public void setLabel(String label)
	{
		this.label = label;
	}

	public SearchModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getLabel()
	{
		return this.label;
	}

	@Override
	public List<IFilterModel> getFilterModels()
	{
		return this.filters;
	}

	@Override
	public IResultModel getResultModel()
	{
		return this.resultModel;
	}

	public void setResultModel(IResultModel resultModel)
	{
		this.resultModel = resultModel;
	}

}
