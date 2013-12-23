package de.pellepelster.myadmin.client.base.modules.dictionary.model.search;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;

public class FilterModel extends BaseModel<Object> implements IFilterModel
{

	private static final long serialVersionUID = 7452528927479882166L;

	private ICompositeModel compositeModel;

	public FilterModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public ICompositeModel getCompositeModel()
	{
		return this.compositeModel;
	}

	public void setCompositeModel(ICompositeModel compositeModel)
	{
		this.compositeModel = compositeModel;
	}

}
