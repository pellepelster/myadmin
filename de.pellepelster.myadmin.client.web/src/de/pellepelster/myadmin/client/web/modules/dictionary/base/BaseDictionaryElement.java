package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public abstract class BaseDictionaryElement<ModelType extends IBaseModel>
{

	private ModelType model;

	private BaseDictionaryElement<? extends IBaseModel> parent;

	public BaseDictionaryElement(ModelType model, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super();
		this.model = model;
		this.parent = parent;
	}

	public ModelType getModel()
	{
		return this.model;
	}

	protected VOWrapper<? extends IBaseVO> getVOWrapper()
	{
		return getParent().getVOWrapper();
	}

	public BaseDictionaryElement<? extends IBaseModel> getParent()
	{
		return this.parent;
	}

	public abstract List<? extends BaseDictionaryElement<?>> getAllChildren();
}
