package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IVOWrapper;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseRootElement;

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

	protected IVOWrapper<? extends IBaseVO> getVOWrapper()
	{
		return getParent().getVOWrapper();
	}

	public BaseDictionaryElement<? extends IBaseModel> getParent()
	{
		return this.parent;
	}

	public BaseRootElement<?> getRootElement()
	{
		return getParent().getRootElement();
	}

	public abstract List<? extends BaseDictionaryElement<?>> getAllChildren();
}
