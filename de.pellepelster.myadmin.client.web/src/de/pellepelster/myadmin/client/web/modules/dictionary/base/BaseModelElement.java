package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public abstract class BaseModelElement<ModelType extends IBaseModel> {

	private ModelType model;
	
	private BaseModelElement<IBaseModel> parent;

	public BaseModelElement(ModelType model, BaseModelElement<IBaseModel> parent) {
		super();
		this.model = model;
		this.parent = parent;
	}

	public ModelType getModel() {
		return model;
	}

	
	protected VOWrapper<IBaseVO> getVOWrapper()
	{
		return getParent().getVOWrapper();
	}
	
	protected BaseModelElement<IBaseModel> getParent()
	{
		return parent;
	}
}
