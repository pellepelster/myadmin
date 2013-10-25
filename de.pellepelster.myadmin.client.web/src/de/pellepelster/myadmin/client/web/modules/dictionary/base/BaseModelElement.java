package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public abstract class BaseModelElement<ModelType extends IBaseModel>
{

	private ModelType model;

	private BaseModelElement<? extends IBaseModel> parent;

	public BaseModelElement(ModelType model, BaseModelElement<? extends IBaseModel> parent)
	{
		super();
		this.model = model;
		this.parent = parent;
	}

	public ModelType getModel()
	{
		return this.model;
	}

	protected VOWrapper<IBaseVO> getVOWrapper()
	{
		return getParent().getVOWrapper();
	}

	protected BaseModelElement<? extends IBaseModel> getParent()
	{
		return this.parent;
	}
}
