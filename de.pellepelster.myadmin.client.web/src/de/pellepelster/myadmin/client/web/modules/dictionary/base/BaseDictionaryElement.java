package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.IBaseDictionaryElement;
import de.pellepelster.myadmin.client.base.modules.dictionary.IBaseRootElement;
import de.pellepelster.myadmin.client.base.modules.dictionary.IVOWrapper;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseDictionaryElement<ModelType extends IBaseModel> implements IBaseDictionaryElement<ModelType>
{

	private ModelType model;

	private BaseDictionaryElement<? extends IBaseModel> parent;

	public BaseDictionaryElement(ModelType model, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super();
		this.model = model;
		this.parent = parent;
	}

	@Override
	public ModelType getModel()
	{
		return this.model;
	}

	@Override
	public IVOWrapper<? extends IBaseVO> getVOWrapper()
	{
		return getParent().getVOWrapper();
	}

	@Override
	public IBaseDictionaryElement<? extends IBaseModel> getParent()
	{
		return this.parent;
	}

	public IBaseRootElement<?> getRootElement()
	{
		return getParent().getRootElement();
	}

	public abstract List<? extends BaseDictionaryElement<?>> getAllChildren();
}
